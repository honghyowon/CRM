package com.study.crmProject.order.service;

import com.study.crmProject.account.entity.Account;
import com.study.crmProject.account.repository.AccountRepository;
import com.study.crmProject.opportunity.controller.request.OpportunityRequest;
import com.study.crmProject.opportunity.entity.Opportunity;
import com.study.crmProject.opportunity.repository.OpportunityRepository;
import com.study.crmProject.order.controller.request.OrderRequest;
import com.study.crmProject.order.entity.Order;
import com.study.crmProject.order.repository.OrderRepository;
import com.study.crmProject.user.entity.User;
import com.study.crmProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OpportunityRepository opportunityRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        // User 조회
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + orderRequest.getUserId()));

        // Opportunity 조회
        Opportunity oppty = opportunityRepository.findById(orderRequest.getOpportunityId())
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + orderRequest.getOpportunityId()));

        // Opportunity에 연결된 Account 가져오기
        Account acc = oppty.getAccount();

        // Opportunity 생성
        Order order = Order.builder()
                .orderName(orderRequest.getOrderName())
                .amount(orderRequest.getAmount())
                .stage(orderRequest.getStage())
                .orderDate(orderRequest.getOrderDate())
                .account(acc)
                .opportunity(oppty)
                .orderOwner(user)
                .build();

        // Opportunity 저장
        return orderRepository.save(order);
    }

    @Transactional
    public Optional<Order> updateOrder(Long id, OrderRequest orderRequest) {
        if (orderRepository.findById(id).isPresent()) {
            User user = userRepository.findById(orderRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + orderRequest.getUserId()));

            Opportunity oppty = opportunityRepository.findById(orderRequest.getOpportunityId())
                    .orElseThrow(() -> new RuntimeException("Account not found with id: " + orderRequest.getOpportunityId()));

            // Opportunity에 연결된 Account 가져오기
            Account acc = oppty.getAccount();

            Order order = Order.builder()
                    .orderId(id)
                    .orderName(orderRequest.getOrderName())
                    .amount(orderRequest.getAmount())
                    .stage(orderRequest.getStage())
                    .orderDate(orderRequest.getOrderDate())
                    .opportunity(oppty)
                    .account(acc)
                    .orderOwner(user)
                    .build();

            return Optional.of(orderRepository.save(order));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteOrder(Long id) {
        if (orderRepository.findById(id).isPresent()) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Order> getOrderByName(String name) {
        return orderRepository.findByOrderName(name);
    }

    public List<Order> getOrdersByStage(String stage) {
        return orderRepository.findByStage(stage);
    }

//    public List<Order> getOrdersByAccountId(Long accountId) {
//        return orderRepository.findByOpportunity_AccountId(accountId);
//    }

    public List<Order> getOrdersByOpportunityId(Long opportunityId) {
        return orderRepository.findByOpportunity_OpportunityId(opportunityId);
    }
}