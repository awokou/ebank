package com.server.api.ebank.service.impl;

import com.server.api.ebank.domain.dto.request.CardDto;
import com.server.api.ebank.domain.dto.request.UpdateCardDto;
import com.server.api.ebank.domain.entity.Card;
import com.server.api.ebank.domain.entity.Customer;
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
        if (card == null) {
            throw new ResourceNotFoundException(
                    String.format("Card for customer with ID %d not found", id));
        }

        return mapToCardDto(card);
    }

    @Override
    @Transactional
    public CardDto createCard(CardDto cardDto) {
        // Recherche du client
        Customer customer = customerRepository.findById(cardDto.customerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Customer with ID %d not found", cardDto.customerId())));

        Card card = new Card();
        card.setBlocked(false);
        card.setBypassed(false);
        card.setOnlinePayment(false);
        card.setInternationalPayment(false);
        card.setExpirationDate(LocalDate.now().plusYears(4));
        card.setCustomer(customer);

        Card savedCard = cardRepository.save(card);

        return mapToCardDto(savedCard);
    }

    @Override
    @Transactional
    public CardDto setEnabled(UpdateCardDto updateCardDto) {

        Card card = cardRepository.findByCustomerId(updateCardDto.customerId());
        if (card == null) {
            throw new ResourceNotFoundException(
                    String.format("Card for customer with ID %d not found", updateCardDto.customerId()));
        }
        card.setBlocked(!updateCardDto.value());

        cardRepository.save(card);

        return mapToCardDto(card);
    }

    @Override
    @Transactional
    public CardDto setOnlinePayment(UpdateCardDto updateCardDto) {

        Card card = cardRepository.findByCustomerId(updateCardDto.customerId());
        if (card == null) {
            throw new ResourceNotFoundException(
                    String.format("Card for customer with ID %d not found", updateCardDto.customerId()));
        }
        card.setOnlinePayment(updateCardDto.value());

        cardRepository.save(card);

        return mapToCardDto(card);
    }

    @Override
    @Transactional
    public CardDto setByPass(UpdateCardDto updateCardDto) {

        Card card = cardRepository.findByCustomerId(updateCardDto.customerId());
        if (card == null) {
            throw new ResourceNotFoundException(
                    String.format("Card for customer with ID %d not found", updateCardDto.customerId()));
        }
        card.setBypassed(updateCardDto.value());

        cardRepository.save(card);

        return mapToCardDto(card);
    }

    @Override
    @Transactional
    public CardDto setInternationalPayment(UpdateCardDto updateCardDto) {

        Card card = cardRepository.findByCustomerId(updateCardDto.customerId());
        if (card == null) {
            throw new ResourceNotFoundException(
                    String.format("Card for customer with ID %d not found", updateCardDto.customerId()));
        }
        card.setInternationalPayment(updateCardDto.value());

        cardRepository.save(card);

        return mapToCardDto(card);
    }

    /**
     * Map Card entity to CardDto.
     *
     * @param card the card entity
     * @return the card data transfer object
     */
    private CardDto mapToCardDto(Card card) {
        // Build immutable CardDto record from Card entity
        return new CardDto(
                card.getId(),
                !card.isBlocked(),
                card.isOnlinePayment(),
                card.isInternationalPayment(),
                card.isBypassed(),
                card.getCustomer().getId()
        );
    }
}

