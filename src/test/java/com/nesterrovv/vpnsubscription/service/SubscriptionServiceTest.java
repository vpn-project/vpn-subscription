package com.nesterrovv.vpnsubscription.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.nesterrovv.vpnsubscription.entity.Subscription;
import com.nesterrovv.vpnsubscription.feign.VpnTokenFeignClient;
import com.nesterrovv.vpnsubscription.repository.SubscriptionRepository;
import com.nesterrovv.vpnsubscription.serivce.SubscriptionService;
import com.nesterrovv.vpnlibrary.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

class SubscriptionServiceTest {

    private SubscriptionService subscriptionService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private VpnTokenFeignClient vpnTokenFeignClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        subscriptionService = new SubscriptionService(subscriptionRepository);
    }

    @Test
    void testCreateSubscription() {
        Date expirationDate = new Date();
        boolean isActive = true;
        Token token = new Token();

        when(subscriptionRepository.save(any(Subscription.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Subscription createdSubscription = subscriptionService.createSubscription(expirationDate, isActive, token);

        assertNotNull(createdSubscription);
        assertEquals(expirationDate, createdSubscription.getExpirationDate());
        assertEquals(isActive, createdSubscription.isActive());
        assertEquals(token, createdSubscription.getToken());
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }

    @Test
    void testGetAllSubscriptions() {
        List<Subscription> mockSubscriptions = Arrays.asList(new Subscription(), new Subscription());

        when(subscriptionRepository.findAll()).thenReturn(mockSubscriptions);

        List<Subscription> allSubscriptions = subscriptionService.getAllSubscriptions();

        assertEquals(mockSubscriptions, allSubscriptions);
        verify(subscriptionRepository, times(1)).findAll();
    }

    @Test
    void testGetSubscriptionById_ExistingSubscription() {
        Integer subscriptionId = 1;
        Subscription mockSubscription = new Subscription();
        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(mockSubscription));

        Optional<Subscription> foundSubscription = subscriptionService.getSubscriptionById(subscriptionId);

        assertTrue(foundSubscription.isPresent());
        assertEquals(mockSubscription, foundSubscription.get());
        verify(subscriptionRepository, times(1)).findById(subscriptionId);
    }

    @Test
    void testGetSubscriptionById_NonExistingSubscription() {
        Integer subscriptionId = 1;
        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());

        Optional<Subscription> foundSubscription = subscriptionService.getSubscriptionById(subscriptionId);

        assertFalse(foundSubscription.isPresent());
        verify(subscriptionRepository, times(1)).findById(subscriptionId);
    }

    @Test
    void testUpdateSubscription() {
        Integer subscriptionId = 1;
        Date expirationDate = new Date();
        boolean isActive = true;
        Token token = new Token();
        Subscription existingSubscription = new Subscription(expirationDate, isActive, token);

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(existingSubscription));
        when(subscriptionRepository.save(any(Subscription.class))).thenAnswer(invocation
            -> invocation.getArgument(0));

        Subscription updatedSubscription = subscriptionService.updateSubscription(subscriptionId, new Date(),
            false, new Token());

        assertNotNull(updatedSubscription);
        assertEquals(subscriptionId, updatedSubscription.getId());
        assertFalse(updatedSubscription.isActive());
        verify(subscriptionRepository, times(1)).findById(subscriptionId);
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }

    @Test
    void testUpdateSubscription_NonExistingSubscription() {
        Integer subscriptionId = 1;

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());

        Subscription updatedSubscription = subscriptionService
            .updateSubscription(subscriptionId, new Date(), false, new Token());

        assertNull(updatedSubscription);
        verify(subscriptionRepository, times(1)).findById(subscriptionId);
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    @Test
    @Disabled
    void testDeleteSubscription() {
        Integer subscriptionId = 1;
        Subscription existingSubscription = new Subscription(new Date(), true, new Token());

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(existingSubscription));
        doNothing().when(vpnTokenFeignClient).deleteToken(existingSubscription.getToken().getId());
        doNothing().when(subscriptionRepository).deleteById(subscriptionId);

        subscriptionService.deleteSubscription(subscriptionId);

        assertFalse(existingSubscription.isActive());
        verify(vpnTokenFeignClient, times(1)).deleteToken(existingSubscription.getToken().getId());
        verify(subscriptionRepository, times(1)).deleteById(subscriptionId);
    }

    @Test
    void testDeleteSubscription_NonExistingSubscription() {
        Integer subscriptionId = 1;

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());

        subscriptionService.deleteSubscription(subscriptionId);

        verify(vpnTokenFeignClient, never()).deleteToken(any(Integer.class));
        verify(subscriptionRepository, never()).deleteById(subscriptionId);
    }

    @Test
    @Disabled
    void testDeactivateSubscription() {
        Integer subscriptionId = 1;
        Subscription existingSubscription = new Subscription(new Date(), true, new Token());

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(existingSubscription));
        doNothing().when(vpnTokenFeignClient).deleteToken(existingSubscription.getToken().getId());
        when(subscriptionRepository.save(any(Subscription.class))).thenAnswer(invocation
            -> invocation.getArgument(0));

        Subscription deactivatedSubscription = subscriptionService.deactivateSubscription(subscriptionId);

        assertNotNull(deactivatedSubscription);
        assertFalse(deactivatedSubscription.isActive());
        verify(vpnTokenFeignClient, times(1)).deleteToken(existingSubscription.getToken().getId());
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }

    @Test
    void testDeactivateSubscription_NonExistingSubscription() {
        Integer subscriptionId = 1;

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());

        Subscription deactivatedSubscription = subscriptionService.deactivateSubscription(subscriptionId);

        assertNull(deactivatedSubscription);
        verify(vpnTokenFeignClient, never()).deleteToken(any(Integer.class));
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    @Test
    void testExtendSubscription() {
        Integer subscriptionId = 1;
        Date originalExpirationDate = new Date();
        Subscription existingSubscription = new Subscription(originalExpirationDate, true, new Token());

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(existingSubscription));
        when(subscriptionRepository.save(any(Subscription.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        Subscription extendedSubscription = subscriptionService.extendSubscription(subscriptionId);

        assertNotNull(extendedSubscription);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(originalExpirationDate);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        Date expectedExpirationDate = calendar.getTime();

        assertEquals(expectedExpirationDate, extendedSubscription.getExpirationDate());
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }

    @Test
    void testExtendSubscription_NonExistingSubscription() {
        Integer subscriptionId = 1;

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());

        Subscription extendedSubscription = subscriptionService.extendSubscription(subscriptionId);

        assertNull(extendedSubscription);
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    @Test
    void testGetSubscriptionsByLinkedTokenId() {
        Long tokenId = 1L;
        List<Subscription> mockSubscriptions = Arrays.asList(new Subscription(), new Subscription());

        when(subscriptionRepository.findSubscriptionsByTokenId(tokenId)).thenReturn(mockSubscriptions);

        List<Subscription> subscriptions = subscriptionService.getSubscriptionsByLinkedTokenId(tokenId);

        assertEquals(mockSubscriptions, subscriptions);
        verify(subscriptionRepository, times(1)).findSubscriptionsByTokenId(tokenId);
    }

}
