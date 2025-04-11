package com.leasing.calculator.service;

import com.leasing.calculator.api.model.exceptions.StatusNotFoundException;
import com.leasing.calculator.api.model.request.aggregates.StatusRequest;
import com.leasing.calculator.api.model.request.primitives.enums.LeaseApplicationStatus;
import com.leasing.calculator.api.model.response.LeaseStatusResponse;
import com.leasing.calculator.domain.aggregates.request.StatusRequestDO;
import com.leasing.calculator.domain.aggregates.response.StatusResponseDO;
import com.leasing.calculator.repository.StatusRepositoryDAO;
import com.leasing.calculator.util.MappingUtil;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    private final StatusRepositoryDAO statusRepository;
    private static final boolean APPLICATIONISOPENED = true;

    public StatusService(StatusRepositoryDAO statusRepository) {
        this.statusRepository = statusRepository;
    }

    public void updateStatusById(StatusRequest statusRequest) {
        statusRepository.updateStatusById(convertToStatusDAORequest(statusRequest));
    }

    public LeaseStatusResponse getStatusById(String id) throws StatusNotFoundException {
        StatusResponseDO statusDAOResponse = statusRepository.getStatusById(id).orElseThrow(() -> new StatusNotFoundException(id));
        return MappingUtil.convertDOResponseIntoStatusResponse(statusDAOResponse);
    }

    public void updateStatusIsRead(String id) {
        statusRepository.updateStatusRead(id, APPLICATIONISOPENED);
    }

    private StatusRequestDO convertToStatusDAORequest(StatusRequest statusRequest) {
        return new StatusRequestDO(statusRequest.id(), LeaseApplicationStatus.valueOf(statusRequest.APPLICATIONSTATUS()));
    }

}
