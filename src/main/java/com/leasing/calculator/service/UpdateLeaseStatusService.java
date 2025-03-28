package com.leasing.calculator.service;

import com.leasing.calculator.api.model.request.aggregates.LeaseStatusRequest;
import com.leasing.calculator.domain.aggregates.request.StatusRequestDO;
import com.leasing.calculator.repository.StatusRepositoryDAO;
import org.springframework.stereotype.Service;

@Service
public class UpdateLeaseStatusService {

    private final StatusRepositoryDAO statusRepositoryDAO;

    public UpdateLeaseStatusService(StatusRepositoryDAO statusRepositoryDAO) {
        this.statusRepositoryDAO = statusRepositoryDAO;
    }

    public void updateLeaseStatus(LeaseStatusRequest leaseStatusRequest) {
        statusRepositoryDAO.updateStatusById(mapLeaseStatusRequestToDO(leaseStatusRequest));
    }

    private StatusRequestDO mapLeaseStatusRequestToDO(LeaseStatusRequest leaseStatusRequest) {
        return new StatusRequestDO(leaseStatusRequest.id(), leaseStatusRequest.applicationStatus());
    }
}