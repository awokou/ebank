package com.server.api.ebank.service;

import com.server.api.ebank.domain.dto.response.HistoryResponse;
import com.server.api.ebank.domain.entity.User;

import java.util.List;

public interface HistoryService {

    void saveHistory(User user, String name);

    List<HistoryResponse> getAllHistory();
}
