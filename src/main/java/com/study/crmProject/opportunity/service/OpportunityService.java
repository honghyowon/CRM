package com.study.crmProject.opportunity.service;

import com.study.crmProject.account.entity.Account;
import com.study.crmProject.account.repository.AccountRepository;
import com.study.crmProject.opportunity.controller.request.OpportunityRequest;
import com.study.crmProject.opportunity.entity.Opportunity;
import com.study.crmProject.opportunity.repository.OpportunityRepository;
import com.study.crmProject.user.entity.User;
import com.study.crmProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OpportunityService {
    private final OpportunityRepository opportunityRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public List<Opportunity> getAllOpportunities() {
        return opportunityRepository.findAll();
    }

    public Optional<Opportunity> getOpportunityById(Long id) {
        return opportunityRepository.findById(id);
    }

    @Transactional
    public Opportunity createOpportunity(OpportunityRequest opportunityRequest) {
        // User 조회
        User user = userRepository.findById(opportunityRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + opportunityRequest.getUserId()));

        // Account 조회
        Account acc = accountRepository.findById(opportunityRequest.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + opportunityRequest.getAccountId()));

        // Opportunity 생성
        Opportunity opportunity = Opportunity.builder()
                .name(opportunityRequest.getName())
                .amount(opportunityRequest.getAmount())
                .stage(opportunityRequest.getStage())
                .closeDate(opportunityRequest.getCloseDate())
                .description(opportunityRequest.getDescription())
                .account(acc)
                .opportunityOwner(user)
                .build();

        // Opportunity 저장
        return opportunityRepository.save(opportunity);
    }

    @Transactional
    public Optional<Opportunity> updateOpportunity(Long id, OpportunityRequest opportunityRequest) {
        if (opportunityRepository.findById(id).isPresent()) {
            User user = userRepository.findById(opportunityRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + opportunityRequest.getUserId()));

            Account acc = accountRepository.findById(opportunityRequest.getAccountId())
                    .orElseThrow(() -> new RuntimeException("Account not found with id: " + opportunityRequest.getAccountId()));

            Opportunity opportunity = Opportunity.builder()
                    .opportunityId(id)
                    .name(opportunityRequest.getName())
                    .amount(opportunityRequest.getAmount())
                    .stage(opportunityRequest.getStage())
                    .closeDate(opportunityRequest.getCloseDate())
                    .description(opportunityRequest.getDescription())
                    .account(acc)
                    .opportunityOwner(user)
                    .build();

            return Optional.of(opportunityRepository.save(opportunity));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteOpportunity(Long id) {
        if (opportunityRepository.findById(id).isPresent()) {
            opportunityRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Opportunity> getOpportunityByName(String name) {
        return opportunityRepository.findByName(name);
    }

    public List<Opportunity> getOpportunitiesByStage(String stage) {
        return opportunityRepository.findByStage(stage);
    }

    public List<Opportunity> getOpportunitiesByAccountId(Long accountId) {
        return opportunityRepository.findByAccount_AccountId(accountId);
    }
}

