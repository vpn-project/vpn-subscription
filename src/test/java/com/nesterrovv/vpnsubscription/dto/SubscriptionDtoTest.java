package com.nesterrovv.vpnsubscription.dto;

import com.nesterrovv.vpnlibrary.Token;
import com.nesterrovv.vpnsubscription.dto.SubscriptionDto;
//import com.nesterrovv.vpnsubscription.vpn.entity.Token;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionDtoTest {

    @Test
    void testDefaultConstructor() {
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        assertNull(subscriptionDto.getExpirationDate());
        assertFalse(subscriptionDto.isActive());
        assertNull(subscriptionDto.getToken());
    }

    @Test
    void testParameterizedConstructor() {
        Date expirationDate = new Date();
        Token token = new Token();
        SubscriptionDto subscriptionDto = new SubscriptionDto(expirationDate, true, token);
        assertEquals(expirationDate, subscriptionDto.getExpirationDate());
        assertTrue(subscriptionDto.isActive());
        assertEquals(token, subscriptionDto.getToken());
    }

    @Test
    void testGettersAndSetters() {
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        Date expirationDate = new Date();
        Token token = new Token();

        subscriptionDto.setExpirationDate(expirationDate);
        subscriptionDto.setActive(true);
        subscriptionDto.setToken(token);

        assertEquals(expirationDate, subscriptionDto.getExpirationDate());
        assertTrue(subscriptionDto.isActive());
        assertEquals(token, subscriptionDto.getToken());
    }

}
