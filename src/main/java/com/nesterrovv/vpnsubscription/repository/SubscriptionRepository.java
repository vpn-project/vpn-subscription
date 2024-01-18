package com.nesterrovv.vpnsubscription.repository;

import com.nesterrovv.vpnsubscription.entity.Subscription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    @Query("SELECT s FROM Subscription s WHERE s.token.id = :tokenId")
    List<Subscription> findSubscriptionsByTokenId(@Param("tokenId") Long tokenId);

}

