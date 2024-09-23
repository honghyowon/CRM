package com.study.crmProject.opportunity.repository;

import com.study.crmProject.opportunity.entity.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
    Optional<Opportunity> findByName(String name);

    List<Opportunity> findByStage(String stage);

    List<Opportunity> findByAccount_AccountId(Long accountId);
}
