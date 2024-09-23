package com.study.crmProject.account.service;

import com.study.crmProject.account.controller.request.AccountRequest;
import com.study.crmProject.account.entity.Account;
import com.study.crmProject.account.repository.AccountRepository;
import com.study.crmProject.user.entity.User;
import com.study.crmProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    @Transactional
    public Account createAccount(AccountRequest accountRequest) {
        User user = userRepository.findById(accountRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + accountRequest.getUserId()));

        Account account = Account.builder()
                .name(accountRequest.getName())
                .phone(accountRequest.getPhone())
                .fax(accountRequest.getFax())
                .website(accountRequest.getWebsite())
                .type(accountRequest.getType())
                .industry(accountRequest.getIndustry())
                .description(accountRequest.getDescription())
                .accountOwner(user)
                .build();

        return accountRepository.save(account);
    }

    @Transactional
    public Optional<Account> updateAccount(Long id, AccountRequest accountRequest) {
        if (accountRepository.findById(id).isPresent()) {
            User user = userRepository.findById(accountRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + accountRequest.getUserId()));

            Account account = Account.builder()
                    .accountId(id)
                    .name(accountRequest.getName())
                    .phone(accountRequest.getPhone())
                    .fax(accountRequest.getFax())
                    .website(accountRequest.getWebsite())
                    .type(accountRequest.getType())
                    .industry(accountRequest.getIndustry())
                    .description(accountRequest.getDescription())
                    .accountOwner(user)
                    .build();

            return Optional.of(accountRepository.save(account));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteAccount(Long id) {
        if (accountRepository.findById(id).isPresent()) {
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Account> getAccountByName(String name) {
        return accountRepository.findByName(name);
    }
}
