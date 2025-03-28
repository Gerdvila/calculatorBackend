package com.leasing.calculator.repository;

import com.leasing.calculator.domain.aggregates.request.CreateLeaseAndRatesRequestDO;

public interface LeaseAndRatesRepositoryDAO {
    void createLeaseAndRate(CreateLeaseAndRatesRequestDO createLeaseAndRatesRequestDO, String pid);
}
