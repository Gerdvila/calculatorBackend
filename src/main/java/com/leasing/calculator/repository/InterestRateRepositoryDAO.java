package com.leasing.calculator.repository;

import com.leasing.calculator.domain.aggregates.response.InterestRateResponseDO;

public interface InterestRateRepositoryDAO {
    InterestRateResponseDO fetchAllInterestRate();
}
