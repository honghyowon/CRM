package com.study.crmProject.order.controller;

import com.study.crmProject.opportunity.controller.request.OpportunityRequest;
import com.study.crmProject.opportunity.entity.Opportunity;
import com.study.crmProject.opportunity.service.OpportunityService;
import com.study.crmProject.order.controller.request.OrderRequest;
import com.study.crmProject.order.entity.Order;
import com.study.crmProject.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 모든 기회 목록 가져오기
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // ID로 특정 기회 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 새로운 기회 생성
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(order);
    }

    // 기존 기회 업데이트
    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest) {
        Optional<Order> updatedOrder = orderService.updateOrder(id, orderRequest);
        return updatedOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 기회 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        boolean isDeleted = orderService.deleteOrder(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // 기회 이름으로 검색
    @GetMapping("/search/name")
    public ResponseEntity<Order> getOrderByName(@RequestParam String name) {
        Optional<Order> order = orderService.getOrderByName(name);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 기회 단계로 검색
    @GetMapping("/search/stage")
    public ResponseEntity<List<Order>> getOrdersByStage(@RequestParam String stage) {
        List<Order> orders = orderService.getOrdersByStage(stage);
        return ResponseEntity.ok(orders);
    }

    // 계정 ID로 검색
//    @GetMapping("/search/account")
//    public ResponseEntity<List<Order>> getOrdersByAccountId(@RequestParam Long accountId) {
//        List<Order> orders = orderService.getOrdersByAccountId(accountId);
//        return ResponseEntity.ok(orders);
//    }

    // 기회 ID로 검색
    @GetMapping("/search/opportunity")
    public ResponseEntity<List<Order>> getOrdersByOpportunityId(@RequestParam Long opportunityId) {
        List<Order> orders = orderService.getOrdersByOpportunityId(opportunityId);
        return ResponseEntity.ok(orders);
    }
}

