package com.study.crmProject.account.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AccountRequest {
    private String name;
    private String phone;
    private String fax;
    private String website;
    private String type;
    private String industry;
    private String description;
    private Long userId; // accountOwner의 userId
}
