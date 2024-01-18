package com.nesterrovv.vpnsubscription.serivce;

import com.nesterrovv.vpnsubscription.entity.Subscription;
import com.nesterrovv.vpnsubscription.feign.VpnTokenFeignClient;
import com.nesterrovv.vpnsubscription.repository.SubscriptionRepository;
import com.nesterrovv.vpnlibrary.Token;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    VpnTokenFeignClient vpnTokenFeignClient;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription createSubscription(Date expirationDate, boolean isActive, Token token) {
        Subscription subscription = new Subscription(expirationDate, isActive, token);
        return save(subscription);
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Optional<Subscription> getSubscriptionById(Integer id) {
        return subscriptionRepository.findById(id);
    }

    public Subscription updateSubscription(Integer id, Date expirationDate, boolean isActive, Token token) {
        Optional<Subscription> optionalSubscription = getSubscriptionById(id);
        if (optionalSubscription.isPresent()) {
            Subscription subscription = optionalSubscription.get();
            subscription.setExpirationDate(expirationDate);
            subscription.setActive(isActive);
            subscription.setToken(token);
            Subscription savedSubscription = save(subscription);
            savedSubscription.setId(id); // Set the id explicitly
            return savedSubscription;
        }
        return null;
    }

    public void deleteSubscription(Integer id) {
        Optional<Subscription> optionalSubscription = getSubscriptionById(id);
        if (optionalSubscription.isPresent()) {
            this.deactivateSubscription(id);
            subscriptionRepository.deleteById(id);
        }
    }

    public Subscription deactivateSubscription(Integer id) {
        Optional<Subscription> optionalSubscription = getSubscriptionById(id);
        if (optionalSubscription.isPresent()) {
            Subscription subscription = optionalSubscription.get();
            Token token = subscription.getToken();
            vpnTokenFeignClient.deleteToken(token.getId());
            subscription.setActive(false);
            return save(subscription);
        }
        return null;
    }


    @SuppressWarnings("MagicNumber")
    public Subscription extendSubscription(Integer id) {
        long plusMonth = 30 * 24 * 60 * 60 * 1000L;
        Optional<Subscription> optionalSubscription = getSubscriptionById(id);
        if (optionalSubscription.isPresent()) {
            Subscription subscription = optionalSubscription.get();
            Date currentExpirationDate = subscription.getExpirationDate();
            Date newExpirationDate = new Date(currentExpirationDate.getTime() + plusMonth);
            subscription.setExpirationDate(newExpirationDate);
            return save(subscription);
        }
        return null;
    }

    private Subscription save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public List<Subscription> getSubscriptionsByLinkedTokenId(Long tokenId) {
        return subscriptionRepository.findSubscriptionsByTokenId(tokenId);
    }

}
