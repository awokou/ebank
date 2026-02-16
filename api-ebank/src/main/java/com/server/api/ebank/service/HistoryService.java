package com.server.api.ebank.service;

import com.server.api.ebank.dto.HistoryResponse;
import com.server.api.ebank.entity.User;

import java.util.List;

public interface HistoryService {

    void saveHistory(User user, String name);

    List<HistoryResponse> getAllHistory();
}
