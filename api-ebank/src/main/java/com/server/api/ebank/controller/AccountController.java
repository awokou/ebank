package com.server.api.ebank.controller;

import com.server.api.ebank.domain.dto.request.OperationDto;
import com.server.api.ebank.domain.dto.request.AccountDto;
import com.server.api.ebank.domain.entity.Operations;
import com.server.api.ebank.service.AccountService;
import com.server.api.ebank.service.OperationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
@Tag(name = "Accounts", description = "API de gestion des comptes bancaires et opérations")
public class AccountController {

    private final AccountService accountService;
    private final OperationService operationService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Créer un compte")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountService.createAccount(accountDto));
    }

    @PostMapping("/credit")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Effectuer un crédit sur un compte")
    public ResponseEntity<Boolean> credit(@RequestBody OperationDto operationDto) {
        return ResponseEntity.ok(operationService.credit(operationDto));
    }

    @PostMapping("/debit")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Effectuer un débit sur un compte")
    public ResponseEntity<Boolean> debit(@RequestBody OperationDto operationDto) {
        return ResponseEntity.ok(operationService.debit(operationDto));
    }

    @GetMapping("/operations/{accountId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Operations>> getOperationsByAccountId(@PathVariable Integer accountId) {
        return ResponseEntity.ok(operationService.getOperationsByAccountId(accountId));
    }
}
