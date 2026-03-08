package com.server.api.ebank.service;

import com.server.api.ebank.domain.dto.request.CardDto;
import com.server.api.ebank.domain.dto.request.UpdateCardDto;

public interface CardService {

    CardDto getCardByCustomerId(Integer id);

    CardDto createCard(CardDto cardDto);

    CardDto setEnabled(UpdateCardDto updateCardDto);

    CardDto setOnlinePayment(UpdateCardDto updateCardDto);

    CardDto setByPass(UpdateCardDto updateCardDto);

    CardDto setInternationalPayment(UpdateCardDto updateCardDto);
}
