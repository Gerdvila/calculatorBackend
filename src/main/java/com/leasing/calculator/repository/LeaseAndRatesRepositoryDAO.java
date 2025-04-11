package com.leasing.calculator.repository;

import com.leasing.calculator.domain.aggregates.request.CreateLeaseAndRatesRequestDO;
import com.leasing.calculator.domain.aggregates.response.LeaseAndRatesResponseDO;

import java.util.List;
import java.util.Optional;

public interface LeaseAndRatesRepositoryDAO {
    void createLeaseAndRate(CreateLeaseAndRatesRequestDO createLeaseAndRatesRequestDO, String pid);

    Optional<LeaseAndRatesResponseDO> getLeaseAndRateById(String pid);

    List<LeaseAndRatesResponseDO> getAllLeaseAndRatesByPage(long pageNumber);
}
