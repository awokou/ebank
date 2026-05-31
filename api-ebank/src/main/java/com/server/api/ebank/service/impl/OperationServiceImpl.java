package com.server.api.ebank.service.impl;

import com.server.api.ebank.domain.dto.request.OperationDto;
import com.server.api.ebank.domain.entity.*;
import com.server.api.ebank.domain.enums.AccountType;
import com.server.api.ebank.exception.ResourceNotFoundException;
import com.server.api.ebank.repository.AccountRepository;
import com.server.api.ebank.repository.OperationRepository;
import com.server.api.ebank.service.OperationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    @Override
    public boolean debit(OperationDto operationDto) {
        // Récupérer le compte et vérifier son existence
        Account account = accountRepository.findById(operationDto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Account with ID %s not found", operationDto.getAccountId())));

        // Vérifier le solde suffisant
        BigDecimal amount = operationDto.getAmount();
        if (account.getBalance().compareTo(amount) < 0) {
            return false;
        }

        // Effectuer le débit
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        // Créer l'opération
        Operations operation = new Operations();
        operation.setAccount(account);
        operation.setType(AccountType.DEBIT);
        operation.setAmount(amount);
        operation.setFavorite(operationDto.isFavorite());
        operation.setDescription(operationDto.getDescription());
        operation.setLibel(operationDto.getLibel());

        operationRepository.save(operation);

        return true;
    }

    @Override
    public boolean credit(OperationDto operationDto) {
        // Récupérer le compte et vérifier son existence
        Account account = accountRepository.findById(operationDto.getAccountId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                String.format("Account with ID %s not found", operationDto.getAccountId())));

        // Effectuer le crédit
        BigDecimal amount = operationDto.getAmount();
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        // Créer l'opération
        Operations operation = new Operations();
        operation.setAccount(account);
        operation.setType(AccountType.CREDIT);
        operation.setAmount(amount);
        operation.setFavorite(operationDto.isFavorite());
        operation.setDescription(operationDto.getDescription());
        operation.setLibel(operationDto.getLibel());

        operationRepository.save(operation);

        return true;
    }

    @Override
    public List<Operations> getOperationsByAccountId(Integer accountId) {
        return operationRepository.findByAccountIdOrderByIdDesc(accountId)
                .stream()
                .toList();
    }
}
