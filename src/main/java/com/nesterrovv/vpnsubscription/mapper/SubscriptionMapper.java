package com.nesterrovv.vpnsubscription.mapper;

import com.nesterrovv.vpnsubscription.dto.SubscriptionDto;
import com.nesterrovv.vpnsubscription.entity.Subscription;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {

    public SubscriptionDto entityToDto(Subscription subscription) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(subscription, SubscriptionDto.class);
    }

    public Subscription dtoToEntity(SubscriptionDto subscriptionDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(subscriptionDto, Subscription.class);
    }

}
