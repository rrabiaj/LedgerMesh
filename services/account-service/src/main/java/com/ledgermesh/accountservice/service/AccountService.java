package com.ledgermesh.accountservice.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ledgermesh.accountservice.Account;
import com.ledgermesh.accountservice.dto.AccountRequestDTO;
import com.ledgermesh.accountservice.dto.AccountResponseDTO;
import com.ledgermesh.accountservice.exception.AccountNotFoundException;
import com.ledgermesh.accountservice.mapper.AccountMapper;
import com.ledgermesh.accountservice.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO request) {
        Account account = accountMapper.toEntity(request);
        Account saved = accountRepository.save(account);
        return accountMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public AccountResponseDTO getAccountById(UUID id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        return accountMapper.toResponse(account);
    }

    @Transactional(readOnly = true)
    public List<AccountResponseDTO> getAccountByUserId(UUID userId) {
        return accountRepository.findByUserId(userId).stream()
                .map(accountMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public AccountResponseDTO updateAccount(UUID id, AccountRequestDTO request) {
        Account existing = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        existing.setAccountName(request.getAccountName());
        existing.setAccountType(request.getAccountType());
        existing.setBalance(request.getBalance());
        if (request.getCurrency() != null) {
            existing.setCurrency(request.getCurrency());
        }
        Account updated = accountRepository.save(existing);
        return accountMapper.toResponse(updated);
    }

    @Transactional
    public void deleteAccount(UUID id) {
        if (!accountRepository.existsById(id)) {
            throw new AccountNotFoundException(id);
        }
        accountRepository.deleteById(id);
    }

}
