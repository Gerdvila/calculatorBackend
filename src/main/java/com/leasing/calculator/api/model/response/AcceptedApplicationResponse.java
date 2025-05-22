package com.leasing.calculator.api.model.response;

import java.math.BigDecimal;

public record AcceptedApplicationResponse(
        BigDecimal thisYearSum,
        BigDecimal lastYearSum
) {
}
