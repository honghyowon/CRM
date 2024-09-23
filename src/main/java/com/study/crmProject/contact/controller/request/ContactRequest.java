package com.study.crmProject.contact.controller.request;

import lombok.Data;

@Data
public class ContactRequest {
    private String name;
    private String phone;
    private String description;
    private Long accountId;
    private Long userId; // contactOwner의 userId
}
