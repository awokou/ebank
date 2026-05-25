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
        Customer customer = customerRepository.findById(cardDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Customer with ID %d not found", cardDto.getCustomerId())));

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

        Card card = cardRepository.findByCustomerId(updateCardDto.getCustomerId());
        if (card == null) {
            throw new ResourceNotFoundException(
                    String.format("Card for customer with ID %d not found", updateCardDto.getCustomerId()));
        }
        card.setBlocked(!updateCardDto.isValue());

        cardRepository.save(card);

        return mapToCardDto(card);
    }

    @Override
    @Transactional
    public CardDto setOnlinePayment(UpdateCardDto updateCardDto) {

        Card card = cardRepository.findByCustomerId(updateCardDto.getCustomerId());
        if (card == null) {
            throw new ResourceNotFoundException(
                    String.format("Card for customer with ID %d not found", updateCardDto.getCustomerId()));
        }
        card.setOnlinePayment(updateCardDto.isValue());

        cardRepository.save(card);

        return mapToCardDto(card);
    }

    @Override
    @Transactional
    public CardDto setByPass(UpdateCardDto updateCardDto) {

        Card card = cardRepository.findByCustomerId(updateCardDto.getCustomerId());
        if (card == null) {
            throw new ResourceNotFoundException(
                    String.format("Card for customer with ID %d not found", updateCardDto.getCustomerId()));
        }
        card.setBypassed(updateCardDto.isValue());

        cardRepository.save(card);

        return mapToCardDto(card);
    }

    @Override
    @Transactional
    public CardDto setInternationalPayment(UpdateCardDto updateCardDto) {

        Card card = cardRepository.findByCustomerId(updateCardDto.getCustomerId());
        if (card == null) {
            throw new ResourceNotFoundException(
                    String.format("Card for customer with ID %d not found", updateCardDto.getCustomerId()));
        }
        card.setInternationalPayment(updateCardDto.isValue());

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

        CardDto cardDto = new CardDto();
        cardDto.setId(card.getId());
        cardDto.setEnabled(!card.isBlocked());
        cardDto.setOnlinePayment(card.isOnlinePayment());
        cardDto.setInternationalPayment(card.isInternationalPayment());
        cardDto.setBypassed(card.isBypassed());
        cardDto.setCustomerId(card.getCustomer().getId());

        return cardDto;
    }
}

