package com.server.api.ebank.service;

import com.server.api.ebank.domain.dto.request.OperationDto;
import com.server.api.ebank.domain.dto.request.VirementDto;
import com.server.api.ebank.domain.entity.Operations;

import java.util.List;

public interface OperationService {

    boolean debit(OperationDto operationDto);

    boolean credit(OperationDto operationDto);

    boolean transfer(VirementDto virementDto);

    boolean transferToSaving(VirementDto virementDto);

    boolean transferToCurrent(VirementDto virementDto);

    List<Operations> getOperationsByAccountId(Integer accountId);

    List<Operations> favoriteOperation(Integer accountId);

    Operations oneFavoriteOperation(Integer id);
}
