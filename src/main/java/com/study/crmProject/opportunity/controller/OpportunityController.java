package com.study.crmProject.opportunity.controller;

import com.study.crmProject.opportunity.controller.request.OpportunityRequest;
import com.study.crmProject.opportunity.entity.Opportunity;
import com.study.crmProject.opportunity.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/opportunity")
@RequiredArgsConstructor
public class OpportunityController {

    @Autowired
    private OpportunityService opportunityService;

    // 모든 기회 목록 가져오기
    @GetMapping
    public ResponseEntity<List<Opportunity>> getAllOpportunities() {
        List<Opportunity> opportunities = opportunityService.getAllOpportunities();
        return ResponseEntity.ok(opportunities);
    }

    // ID로 특정 기회 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<Opportunity> getOpportunityById(@PathVariable Long id) {
        Optional<Opportunity> opportunity = opportunityService.getOpportunityById(id);
        return opportunity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 새로운 기회 생성
    @PostMapping("/create")
    public ResponseEntity<Opportunity> createOpportunity(@RequestBody OpportunityRequest opportunityRequest) {
        Opportunity opportunity = opportunityService.createOpportunity(opportunityRequest);
        return ResponseEntity.ok(opportunity);
    }

    // 기존 기회 업데이트
    @PutMapping("/update/{id}")
    public ResponseEntity<Opportunity> updateOpportunity(@PathVariable Long id, @RequestBody OpportunityRequest opportunityRequest) {
        Optional<Opportunity> updatedOpportunity = opportunityService.updateOpportunity(id, opportunityRequest);
        return updatedOpportunity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 기회 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOpportunity(@PathVariable Long id) {
        boolean isDeleted = opportunityService.deleteOpportunity(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // 기회 이름으로 검색
    @GetMapping("/search/name")
    public ResponseEntity<Opportunity> getOpportunityByName(@RequestParam String name) {
        Optional<Opportunity> opportunity = opportunityService.getOpportunityByName(name);
        return opportunity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 기회 단계로 검색
    @GetMapping("/search/stage")
    public ResponseEntity<List<Opportunity>> getOpportunitiesByStage(@RequestParam String stage) {
        List<Opportunity> opportunities = opportunityService.getOpportunitiesByStage(stage);
        return ResponseEntity.ok(opportunities);
    }

    // 계정 ID로 검색
    @GetMapping("/search/account")
    public ResponseEntity<List<Opportunity>> getOpportunitiesByAccountId(@RequestParam Long accountId) {
        List<Opportunity> opportunities = opportunityService.getOpportunitiesByAccountId(accountId);
        return ResponseEntity.ok(opportunities);
    }
}
