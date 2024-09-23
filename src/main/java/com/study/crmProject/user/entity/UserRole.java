package com.study.crmProject.user.entity;

import com.study.crmProject.common.status.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_role_tb")
@Getter
@NoArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "id"))
    private User user;

    @Builder
    private UserRole(Long id, Role role, User user) {
        this.id = id;
        this.role = role;
        this.user = user;
    }
}
