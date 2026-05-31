package com.server.api.ebank.service.impl;

import com.server.api.ebank.domain.dto.response.HistoryResponse;
import com.server.api.ebank.domain.entity.History;
import com.server.api.ebank.domain.entity.User;
import com.server.api.ebank.repository.HistoryRepository;
import com.server.api.ebank.service.HistoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    @Override
    @Transactional
    public void saveHistory(User user, String name) {

        History history = new History();
        history.setUser(user);
        history.setName(name);

        historyRepository.save(history);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoryResponse> getAllHistory() {
        return historyRepository.findAll()
                .stream()
                .map(this::mapToHistoriqueResponse)
                .toList();
    }

    /**
     * Maps a History entity to a HistoryResponse.
     *
     * @param history the history entity
     * @return the history response object
     */
    private HistoryResponse mapToHistoriqueResponse(History history) {
        HistoryResponse response = new HistoryResponse();
        response.setId(history.getId());
        response.setName(history.getName());
        response.setUser(history.getUser().getName());
        return response;
    }
}
