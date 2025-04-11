package com.leasing.calculator.repository;

import com.leasing.calculator.domain.aggregates.request.StatusRequestDO;
import com.leasing.calculator.domain.aggregates.response.StatusResponseDO;

import java.util.List;
import java.util.Optional;

public interface StatusRepositoryDAO {
    void createStatus(String id, boolean isHighRisk);
    void updateStatusById(StatusRequestDO status);
    List<StatusResponseDO> getAllStatusByPage(long pageNumber);
    Optional<StatusResponseDO> getStatusById(String id);
    void updateStatusRead(String id, boolean isOpened);
}
