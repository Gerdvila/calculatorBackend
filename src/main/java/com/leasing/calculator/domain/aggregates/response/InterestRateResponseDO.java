package com.leasing.calculator.domain.aggregates.response;

import java.math.BigDecimal;

public record InterestRateResponseDO(
        BigDecimal regular,
        BigDecimal eco
){}