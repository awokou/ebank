package com.server.api.ebank.service.impl;

import com.server.api.ebank.domain.dto.request.OperationDto;
import com.server.api.ebank.domain.dto.request.VirementDto;
import com.server.api.ebank.domain.entity.*;
import com.server.api.ebank.domain.enums.AccountType;
import com.server.api.ebank.domain.enums.OperationType;
import com.server.api.ebank.exception.ResourceNotFoundException;
import com.server.api.ebank.repository.AccountRepository;
import com.server.api.ebank.repository.OperationRepository;
import com.server.api.ebank.service.OperationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    @Override
    public boolean debit(OperationDto operationDto) {
        if (AccountType.CA.name().equals(operationDto.getAccountType())) {
            CurrentAccount currentAccount = (CurrentAccount) accountRepository.findById(operationDto.getAccountId())
                    .orElse(null);
            if (currentAccount != null && currentAccount.getBalance() >= operationDto.getAmount()) {
                currentAccount.setBalance(currentAccount.getBalance() - operationDto.getAmount());
                accountRepository.save(currentAccount);
            }
        }

        // Handle savings accounts
        if (AccountType.SA.name().equals(operationDto.getAccountType())) {
            SavingAccount savingAccount = (SavingAccount) accountRepository.findById(operationDto.getAccountId())
                    .orElse(null);
            if (savingAccount != null && savingAccount.getBalance() >= operationDto.getAmount()) {
                savingAccount.setBalance(savingAccount.getBalance() - operationDto.getAmount());
                accountRepository.save(savingAccount);
            }
        }

        // Récupérer le compte et vérifier son existence
        Account account = accountRepository.findById(operationDto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Account with ID %s not found", operationDto.getAccountId())));

        // Create the operation
        Operations operation = new Operations();
        operation.setAccount(account);
        operation.setType(OperationType.DEBIT);
        operation.setAmount(operationDto.getAmount());
        operation.setFavorite(operationDto.isFavorite());
        operation.setDescription(operationDto.getDescription());
        operation.setLibel(operationDto.getLibele());

        operationRepository.save(operation);

        return true;
    }

    @Override
    public boolean credit(OperationDto operationDto) {
        if (AccountType.CA.name().equals(operationDto.getAccountType())) {
            CurrentAccount currentAccount = (CurrentAccount) accountRepository
                    .findById(operationDto.getAccountId()).orElse(null);
            if (currentAccount != null) {
                currentAccount.setBalance(currentAccount.getBalance() + operationDto.getAmount());
                accountRepository.save(currentAccount);
            } else {
                return false;
            }
        }

        if (AccountType.SA.name().equals(operationDto.getAccountType())) {
            SavingAccount savingAccount = (SavingAccount) accountRepository
                    .findById(operationDto.getAccountId()).orElse(null);
            if (savingAccount != null) {
                savingAccount.setBalance(savingAccount.getBalance() + operationDto.getAmount());
                accountRepository.save(savingAccount);
            } else {
                return false;
            }
        }
        // Récupérer le compte et vérifier son existence
        Account account = accountRepository.findById(operationDto.getAccountId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                String.format("Account with ID %s not found", operationDto.getAccountId())));

        Operations operation = new Operations();
        operation.setAccount(account);
        operation.setType(OperationType.CREDIT);
        operation.setAmount(operationDto.getAmount());
        operation.setFavorite(operationDto.isFavorite());
        operation.setDescription(operationDto.getDescription());
        operation.setLibel(operationDto.getLibele());

        operationRepository.save(operation);

        return true;
    }

    @Override
    public boolean transfer(VirementDto virementDto) {

        OperationDto creditOpt = new OperationDto();
        creditOpt.setAccountId(virementDto.getAccountReceiver());
        creditOpt.setAccountType(AccountType.CA.name());
        creditOpt.setAmount(virementDto.getAmount());
        creditOpt.setDescription(virementDto.getDescription());

        OperationDto debitOpt = new OperationDto();
        debitOpt.setAccountId(virementDto.getAccountSender());
        debitOpt.setAccountType(AccountType.CA.name());
        debitOpt.setAmount(virementDto.getAmount());
        debitOpt.setDescription(virementDto.getDescription());
        debitOpt.setFavorite(virementDto.isFavorite());
        debitOpt.setLibele(virementDto.getLibel());

        credit(creditOpt);
        debit(debitOpt);

        return true;
    }

    @Override
    public boolean transferToSaving(VirementDto virementDto) {

        OperationDto creditOpt = new OperationDto();
        creditOpt.setAccountId(virementDto.getAccountReceiver());
        creditOpt.setAccountType(AccountType.SA.name());
        creditOpt.setAmount(virementDto.getAmount());
        creditOpt.setDescription(virementDto.getDescription());

        OperationDto debitOpt = new OperationDto();
        debitOpt.setAccountId(virementDto.getAccountSender());
        debitOpt.setAccountType(AccountType.SA.name());
        debitOpt.setAmount(virementDto.getAmount());
        debitOpt.setDescription(virementDto.getDescription());
        debitOpt.setFavorite(virementDto.isFavorite());

        credit(creditOpt);
        debit(debitOpt);

        return true;
    }

    @Override
    public boolean transferToCurrent(VirementDto virementDto) {

        OperationDto creditOpt = new OperationDto();
        creditOpt.setAccountId(virementDto.getAccountReceiver());
        creditOpt.setAccountType(AccountType.CA.name());
        creditOpt.setAmount(virementDto.getAmount());
        creditOpt.setDescription(virementDto.getDescription());

        OperationDto debitOpt = new OperationDto();
        debitOpt.setAccountId(virementDto.getAccountSender());
        debitOpt.setAccountType(AccountType.SA.name());
        debitOpt.setAmount(virementDto.getAmount());
        debitOpt.setDescription(virementDto.getDescription());
        debitOpt.setFavorite(virementDto.isFavorite());

        credit(creditOpt);
        debit(debitOpt);

        return true;
    }

    @Override
    public List<Operations> getOperationsByAccountId(Integer accountId) {
        return operationRepository.findByAccountIdOrderByIdDesc(accountId)
                .stream()
                .toList();
    }

    @Override
    public List<Operations> favoriteOperation(Integer accountId) {
        return null;
    }

    @Override
    public Operations oneFavoriteOperation(Integer id) {
        return operationRepository.oneFavoriteOperation(id);
    }
}
