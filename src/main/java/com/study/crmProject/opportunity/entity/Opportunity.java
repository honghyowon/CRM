package com.study.crmProject.opportunity.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.study.crmProject.account.entity.Account;
import com.study.crmProject.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "opportunity_tb")
@Getter
@NoArgsConstructor
public class Opportunity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long opportunityId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String stage;

    @Column(nullable = false)
    private LocalDate closeDate;

    @Lob
    @Column(nullable = true)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "opportunity_accountid", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opportunity_owner_id", nullable = false)
    @JsonBackReference
    private User opportunityOwner;

    @Column(nullable = false)
    private LocalDate createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }

    @Builder
    public Opportunity(Long opportunityId, String name, BigDecimal amount, String stage, LocalDate closeDate, String description, Account account, User opportunityOwner) {
        this.opportunityId = opportunityId;
        this.name = name;
        this.amount = amount;
        this.stage = stage;
        this.closeDate = closeDate;
        this.description = description;
        this.account = account;
        this.opportunityOwner = opportunityOwner;
        this.createdDate = LocalDate.now();
    }
}
