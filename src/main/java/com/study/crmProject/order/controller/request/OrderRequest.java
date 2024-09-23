package com.study.crmProject.order.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {
    private String orderName;
    private BigDecimal amount;
    private String stage;
    private LocalDate orderDate;
    private Long opportunityId;
//    private Long accountId;
    private Long userId;

    public OrderRequest(String orderName, BigDecimal amount, String stage, LocalDate orderDate, Long opportunityId, Long userId) {
        this.orderName      = orderName;
        this.amount         = amount;
        this.stage          = stage;
        this.orderDate      = orderDate;
        this.opportunityId  = opportunityId;
        this.userId         = userId;
    }
}
