package com.leasing.calculator.domain.aggregates.response;

import java.math.BigDecimal;

public record AcceptedApplicationResponseDO(
        BigDecimal thisYearSum,
        BigDecimal lastYearSum
) {
}