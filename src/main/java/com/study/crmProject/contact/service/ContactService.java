package com.study.crmProject.contact.service;

import com.study.crmProject.account.entity.Account;
import com.study.crmProject.account.repository.AccountRepository;
import com.study.crmProject.contact.controller.request.ContactRequest;
import com.study.crmProject.contact.entity.Contact;
import com.study.crmProject.contact.repository.ContactRepository;
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
public class ContactService {
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    @Transactional
    public Contact createContact(ContactRequest contactRequest) {
        // User 조회
        User user = userRepository.findById(contactRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + contactRequest.getUserId()));

        // Account 조회
        Account acc = accountRepository.findById(contactRequest.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + contactRequest.getAccountId()));

        // Contact 생성
        Contact contact = Contact.builder()
                .name(contactRequest.getName())
                .phone(contactRequest.getPhone())
                .description(contactRequest.getDescription())
                .account(acc)  // accountId 대신 acc 객체 설정
                .contactOwner(user)
                .build();

        // Contact 저장
        return contactRepository.save(contact);
    }

    @Transactional
    public Optional<Contact> updateContact(Long id, ContactRequest contactRequest) {
        if (contactRepository.findById(id).isPresent()) {
            User user = userRepository.findById(contactRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + contactRequest.getUserId()));

            Account acc = accountRepository.findById(contactRequest.getAccountId())
                    .orElseThrow(() -> new RuntimeException("Account not found with id: " + contactRequest.getUserId()));

            Contact contact = Contact.builder()
                    .contactId(id)
                    .name(contactRequest.getName())
                    .phone(contactRequest.getPhone())
                    .description(contactRequest.getDescription())
                    .account(acc)
                    .contactOwner(user)
                    .build();

            return Optional.of(contactRepository.save(contact));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteContact(Long id) {
        if (contactRepository.findById(id).isPresent()) {
            contactRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Contact> getContactByName(String name) {
        return contactRepository.findByName(name);
    }
}
