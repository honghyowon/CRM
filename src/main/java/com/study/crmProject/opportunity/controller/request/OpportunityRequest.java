package com.study.crmProject.opportunity.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class OpportunityRequest {
    private String name;
    private BigDecimal amount;
    private String stage;
    private LocalDate closeDate;
    private String description;
    private Long accountId;
    private Long userId;

    public OpportunityRequest(String name, BigDecimal amount, String stage, LocalDate closeDate, String description, Long accountId, Long userId) {
        this.name = name;
        this.amount = amount;
        this.stage = stage;
        this.closeDate = closeDate;
        this.description = description;
        this.accountId = accountId;
        this.userId = userId;
    }
}

