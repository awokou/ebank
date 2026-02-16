package com.server.api.ebank.controller;

import com.server.api.ebank.dto.CurrentAccountDto;
import com.server.api.ebank.dto.OperationDto;
import com.server.api.ebank.dto.SavingAccountDto;
import com.server.api.ebank.dto.VirementDto;
import com.server.api.ebank.entity.Operations;
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

    @PostMapping("/current")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Créer un compte courant")
    public ResponseEntity<CurrentAccountDto> createCurrentAccount(@RequestBody CurrentAccountDto currentAccountDto) {
        return ResponseEntity.ok(accountService.createCurrentAccount(currentAccountDto));
    }

    @PostMapping("/saving")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Créer un compte épargne")
    public ResponseEntity<SavingAccountDto> createSavingAccount(@RequestBody SavingAccountDto savingAccountDto) {
        return ResponseEntity.ok(accountService.createSavingAccount(savingAccountDto));
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

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Effectuer un virement entre comptes")
    public ResponseEntity<Boolean> transfer(@RequestBody VirementDto virementDto) {
        return ResponseEntity.ok(operationService.transfer(virementDto));
    }

    @PostMapping("/transferToSaving")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Boolean> transferToSaving(@RequestBody VirementDto virementDto) {
        return ResponseEntity.ok(operationService.transferToSaving(virementDto));
    }

    @PostMapping("/transferToCurrent")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Boolean> transferToCurrent(@RequestBody VirementDto virementDto) {
        return ResponseEntity.ok(operationService.transferToCurrent(virementDto));
    }

    @GetMapping("/operations/{accountId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Operations>> getOperationsByAccountId(@PathVariable Integer accountId) {
        return ResponseEntity.ok(operationService.getOperationsByAccountId(accountId));
    }

    @GetMapping("/operations/favorite/{accountId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Récupérer les opérations favorites d'un compte")
    public ResponseEntity<List<Operations>> getFavoriteOperations(@PathVariable Integer accountId) {
        return ResponseEntity.ok(operationService.favoriteOperation(accountId));
    }

    @GetMapping("/operations/onefavorite/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Operations> oneFavoriteOperation(@PathVariable Integer id) {
        return ResponseEntity.ok(operationService.oneFavoriteOperation(id));
    }
}
