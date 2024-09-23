package com.study.crmProject.user.repository;

import com.study.crmProject.common.status.Role;
import com.study.crmProject.user.entity.User;
import com.study.crmProject.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUser(User user);
    Optional<UserRole> findByUserAndRole(User user, Role role);
}
