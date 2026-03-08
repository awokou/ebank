package com.server.api.ebank.controller;

import com.server.api.ebank.domain.dto.request.CardDto;
import com.server.api.ebank.domain.dto.request.UpdateCardDto;
import com.server.api.ebank.service.CardService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CardDto> getCard(@PathVariable Integer id) {
        return ResponseEntity.ok(cardService.getCardByCustomerId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CardDto> createCard(@RequestBody CardDto cardDto) {
        return ResponseEntity.ok(cardService.createCard(cardDto));
    }

    @PutMapping("/setEnabled")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CardDto> setEnabled(@RequestBody UpdateCardDto updateCardDto) {
        return ResponseEntity.ok(cardService.setEnabled(updateCardDto));
    }

    @PutMapping("/setOnlinePayment")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CardDto> setOnlinePayment(@RequestBody UpdateCardDto updateCardDto) {
        return ResponseEntity.ok(cardService.setOnlinePayment(updateCardDto));
    }

    @PutMapping("/setByPass")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CardDto> setByPass(@RequestBody UpdateCardDto updateCardDto) {
        return ResponseEntity.ok(cardService.setByPass(updateCardDto));
    }

    @PutMapping("/setInternationalPayment")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CardDto> setInternationalPayment(@RequestBody UpdateCardDto updateCardDto) {
        return ResponseEntity.ok(cardService.setInternationalPayment(updateCardDto));
    }
}
