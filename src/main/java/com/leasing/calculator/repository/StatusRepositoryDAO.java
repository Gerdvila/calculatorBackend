package com.leasing.calculator.repository;

import com.leasing.calculator.domain.aggregates.request.StatusRequestDO;

public interface StatusRepositoryDAO {
    public void createStatus(String id, boolean isHighRisk);
    void updateStatusById(StatusRequestDO status);

}
