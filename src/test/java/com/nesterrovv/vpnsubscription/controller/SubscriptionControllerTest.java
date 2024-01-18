package com.nesterrovv.vpnsubscription.controller;

import com.nesterrovv.vpnlibrary.Token;
import com.nesterrovv.vpnsubscription.entity.Subscription;
import com.nesterrovv.vpnsubscription.feign.VpnTokenFeignClient;
import com.nesterrovv.vpnsubscription.serivce.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SubscriptionControllerTest {

    @Mock
    private SubscriptionService subscriptionService;

    @Mock
    private VpnTokenFeignClient vpnTokenFeignClient;

    @InjectMocks
    private SubscriptionController subscriptionController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController).build();
    }

    @Test
    void testGetAllSubscriptions() {
        when(subscriptionService.getAllSubscriptions()).thenReturn(Collections.emptyList());
        ResponseEntity<List<Subscription>> responseEntity = subscriptionController.getAllSubscriptions();
        verify(subscriptionService).getAllSubscriptions();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testGetSubscriptionById() {
        when(subscriptionService.getSubscriptionById(1)).thenReturn(Optional.of(new Subscription()));
        ResponseEntity<Subscription> responseEntity = subscriptionController.getSubscriptionById(1);
        verify(subscriptionService).getSubscriptionById(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testGetSubscriptionByIdNotFound() {
        when(subscriptionService.getSubscriptionById(1)).thenReturn(Optional.empty());
        ResponseEntity<Subscription> responseEntity = subscriptionController.getSubscriptionById(1);
        verify(subscriptionService).getSubscriptionById(1);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    @Disabled
    void testCreateSubscription() {
        when(vpnTokenFeignClient.generateToken()).thenReturn(new Token());
        when(subscriptionService.createSubscription(any(Date.class), anyBoolean(), any(Token.class)))
            .thenReturn(new Subscription());
        ResponseEntity<Subscription> responseEntity = subscriptionController.createSubscription(new Date());
        verify(vpnTokenFeignClient).generateToken();
        verify(subscriptionService).createSubscription(any(Date.class), anyBoolean(), any(Token.class));
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testDeleteSubscription() {
        ResponseEntity<Void> responseEntity = subscriptionController.deleteSubscription(1);
        verify(subscriptionService).deleteSubscription(1);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void testDeactivateSubscription() {
        when(subscriptionService.deactivateSubscription(anyInt())).thenReturn(new Subscription());
        ResponseEntity<Subscription> responseEntity = subscriptionController.deactivateSubscription(1);
        verify(subscriptionService).deactivateSubscription(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testExtendSubscription() {
        when(subscriptionService.extendSubscription(anyInt())).thenReturn(new Subscription());
        ResponseEntity<Subscription> responseEntity = subscriptionController.extendSubscription(1);
        verify(subscriptionService).extendSubscription(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

}
