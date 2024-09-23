package com.study.crmProject.contact.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.study.crmProject.account.entity.Account;
import com.study.crmProject.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "contact_tb")
@Getter
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    @Column(nullable = false, length = 30, unique = true)
    private String name;

    @Column(nullable = true, length = 20)
    private String phone;

    @Lob
    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.EAGER)  // 일대대 관계 설정
    @JoinColumn(name = "contact_accountid", nullable = false)  // 외래 키 컬럼 이름 지정
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)  // 일대다 관계 설정
    @JoinColumn(name = "contact_owner_id", nullable = false)  // 외래 키 컬럼 이름 지정
    @JsonBackReference
//    @JsonIgnore
    private User contactOwner;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }

    @Builder
    private Contact(Long contactId, String name, String phone, String description, LocalDate createdDate, Account account, User contactOwner) {
        this.contactId = contactId;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.createdDate = createdDate != null ? createdDate : LocalDate.now();
        this.account = account;
        this.contactOwner = contactOwner;
    }
}
