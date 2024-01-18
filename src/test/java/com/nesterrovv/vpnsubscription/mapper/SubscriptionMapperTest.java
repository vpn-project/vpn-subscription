package com.nesterrovv.vpnsubscription.mapper;

import com.nesterrovv.vpnsubscription.dto.SubscriptionDto;
import com.nesterrovv.vpnsubscription.entity.Subscription;
import com.nesterrovv.vpnsubscription.mapper.SubscriptionMapper;
import org.junit.jupiter.api.Test;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SubscriptionMapperTest {

    @Test
    void testEntityToDto() {
        // Arrange
        Subscription subscription = new Subscription();
        subscription.setExpirationDate(new Date());
        SubscriptionMapper subscriptionMapper = new SubscriptionMapper();
        // Act
        SubscriptionDto subscriptionDto = subscriptionMapper.entityToDto(subscription);
        // Assert
        assertEquals(subscription.getExpirationDate(), subscriptionDto.getExpirationDate());
    }

    @Test
    void testDtoToEntity() {
        // Arrange
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setExpirationDate(new Date());
        SubscriptionMapper subscriptionMapper = new SubscriptionMapper();
        // Act
        Subscription subscription = subscriptionMapper.dtoToEntity(subscriptionDto);
        // Assert
        assertEquals(subscription.getExpirationDate(), subscriptionDto.getExpirationDate());
    }

}
