package com.study.crmProject.order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.study.crmProject.account.entity.Account;
import com.study.crmProject.opportunity.entity.Opportunity;
import com.study.crmProject.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "order_tb")
@Getter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private String orderName;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String stage;

    @Column(nullable = false)
    private LocalDate orderDate;

    @Column(nullable = false)
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonManagedReference  // 순환 참조 방지 (Account는 직렬화)
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "opportunity_id", nullable = false)
    @JsonManagedReference  // 순환 참조 방지 (Opportunity 직렬화)
    private Opportunity opportunity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_owner_id", nullable = false)
    @JsonIgnore
    private User orderOwner;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }

    @Builder
    public Order(Long orderId, String orderName, BigDecimal amount, String stage, LocalDate orderDate, Account account, Opportunity opportunity, User orderOwner) {
        this.orderId        = orderId;
        this.orderName      = orderName;
        this.amount         = amount;
        this.stage          = stage;
        this.orderDate      = orderDate;
        this.createdDate    = LocalDate.now();
        this.account        = account;
        this.opportunity    = opportunity;
        this.orderOwner     = orderOwner;
    }
}
