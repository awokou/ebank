package com.server.api.ebank.service.impl;

import com.server.api.ebank.dto.CardDto;
import com.server.api.ebank.dto.UpdateCardDto;
import com.server.api.ebank.entity.Card;
import com.server.api.ebank.entity.Customer;
import com.server.api.ebank.exception.ResourceNotFoundException;
import com.server.api.ebank.repository.CardRepository;
import com.server.api.ebank.repository.CustomerRepository;
import com.server.api.ebank.service.CardService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public CardDto getCardByCustomerId(Integer id) {

        Card card = cardRepository.findByCustomerId(id);

        return mapToCardDto(card);
    }

    @Override
    @Transactional
    public CardDto createCard(CardDto cardDto) {
        // Recherche du client
        Customer customer = customerRepository.findById(cardDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Customer with ID %d not found", cardDto.getCustomerId())));

        Card card = new Card();
        card.setEnabled(true);
        card.setBypassed(false);
        card.setOnlinePayment(false);
        card.setInternationalPayment(false);
        card.setExpirationDate(LocalDate.now().plusYears(4));
        card.setCustomer(customer);
        
        cardRepository.save(card);

        return cardDto;
    }

    @Override
    @Transactional
    public CardDto setEnabled(UpdateCardDto updateCardDto) {
        
        Card card = cardRepository.findByCustomerId(updateCardDto.getCustomerId());
        card.setEnabled(updateCardDto.isValue());
        
        cardRepository.save(card);

        return mapToCardDto(card);
    }

    @Override
    @Transactional
    public CardDto setOnlinePayment(UpdateCardDto updateCardDto) {
        
        Card card = cardRepository.findByCustomerId(updateCardDto.getCustomerId());
        card.setOnlinePayment(updateCardDto.isValue());
        
        cardRepository.save(card);

        return mapToCardDto(card);
    }

    @Override
    @Transactional
    public CardDto setByPass(UpdateCardDto updateCardDto) {
        
        Card card = cardRepository.findByCustomerId(updateCardDto.getCustomerId());
        card.setBypassed(updateCardDto.isValue());
        
        cardRepository.save(card);

        return mapToCardDto(card);
    }

    @Override
    @Transactional
    public CardDto setInternationalPayment(UpdateCardDto updateCardDto) {
       
        Card card = cardRepository.findByCustomerId(updateCardDto.getCustomerId());
        card.setInternationalPayment(updateCardDto.isValue());
        
        cardRepository.save(card);

        return mapToCardDto(card);
    }

    /**
     * Map Card entity to CardDto.
     *
     * @param card the taxe entity
     * @return the card data transfer object
     */
    private CardDto mapToCardDto(Card card) {

        CardDto cardDto = new CardDto();
        cardDto.setId(card.getId());
        cardDto.setEnabled(true);
        cardDto.setOnlinePayment(false);
        cardDto.setInternationalPayment(false);
        cardDto.setBypassed(false);

        return cardDto;
    }
}
