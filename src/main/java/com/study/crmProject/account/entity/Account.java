package com.study.crmProject.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.study.crmProject.contact.entity.Contact;
import com.study.crmProject.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "account_tb")
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false, length = 30, unique = true)
    private String name;

    @Column(nullable = true, length = 20)
    private String phone;

    @Column(nullable = true, length = 20)
    private String fax;

    @Column(nullable = true, length = 40)
    private String website;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(nullable = false, length = 40)
    private String industry;

    @Lob
    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private LocalDate createdDate;

    // 엔티티가 처음 저장될 때 createdDate를 현재 날짜로 설정
    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)  // 다대일 관계 설정
    @JoinColumn(name = "account_owner_id", nullable = false)  // 외래 키 컬럼 이름 지정
    @JsonIgnore
    private User accountOwner;

    @Builder
    private Account(Long accountId, String name, String phone, String fax, String website, String type, String industry, String description, LocalDate createdDate, User accountOwner) {
        this.accountId = accountId;
        this.name = name;
        this.phone = phone;
        this.fax = fax;
        this.website = website;
        this.type = type;
        this.industry = industry;
        this.description = description;
        this.createdDate = createdDate != null ? createdDate : LocalDate.now();
        this.accountOwner = accountOwner;
    }
}