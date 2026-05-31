package com.server.api.ebank.service;

import com.server.api.ebank.domain.dto.request.OperationDto;
import com.server.api.ebank.domain.entity.Operations;

import java.util.List;

public interface OperationService {

    boolean debit(OperationDto operationDto);

    boolean credit(OperationDto operationDto);

    List<Operations> getOperationsByAccountId(Integer accountId);
}
