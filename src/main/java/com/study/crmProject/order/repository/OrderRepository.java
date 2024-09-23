package com.study.crmProject.order.repository;

import com.study.crmProject.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderName(String name);

    List<Order> findByStage(String stage);

    List<Order> findByOpportunity_Account_AccountId(Long accountId);

    List<Order> findByOpportunity_OpportunityId(Long opportunityId);
}
