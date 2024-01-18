package com.nesterrovv.vpnsubscription.entity;

import com.nesterrovv.vpnsubscription.entity.Subscription;
import com.nesterrovv.vpnlibrary.Token;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionTest {

    @Test
    void testDefaultConstructor() {
        Subscription subscription = new Subscription();
        assertNull(subscription.getId());
        assertNull(subscription.getExpirationDate());
        assertFalse(subscription.isActive());
        assertNull(subscription.getToken());
    }

    @Test
    void testParameterizedConstructor() {
        Date expirationDate = new Date();
        Token token = new Token();

        Subscription subscription = new Subscription(expirationDate, true, token);

        assertNull(subscription.getId());
        assertEquals(expirationDate, subscription.getExpirationDate());
        assertTrue(subscription.isActive());
        assertEquals(token, subscription.getToken());
    }

    @Test
    void testGettersAndSetters() {
        Subscription subscription = new Subscription();
        Date expirationDate = new Date();
        Token token = new Token();

        subscription.setId(1);
        subscription.setExpirationDate(expirationDate);
        subscription.setActive(true);
        subscription.setToken(token);

        assertEquals(1, subscription.getId());
        assertEquals(expirationDate, subscription.getExpirationDate());
        assertTrue(subscription.isActive());
        assertEquals(token, subscription.getToken());
    }

    @Test
    void testEqualsAndHashCode() {
        Date expirationDate1 = new Date();
        Token token1 = new Token();

        Date expirationDate2 = new Date();
        Token token2 = new Token();

        Subscription subscription1 = new Subscription(expirationDate1, true, token1);
        Subscription subscription2 = new Subscription(expirationDate1, true, token1);
        Subscription subscription3 = new Subscription(expirationDate1, true, token1);
        Subscription subscription4 = new Subscription(expirationDate2, true, token1);
        Subscription subscription5 = new Subscription(expirationDate1, false, token1);
        Subscription subscription6 = new Subscription(expirationDate1, true, token2);

        assertEquals(subscription1, subscription2);
        assertEquals(subscription1, subscription3);
        assertEquals(subscription1, subscription4);
        assertNotEquals(subscription1, subscription5);
        assertEquals(subscription1, subscription6);

        assertEquals(subscription1.hashCode(), subscription2.hashCode());
    }
}
