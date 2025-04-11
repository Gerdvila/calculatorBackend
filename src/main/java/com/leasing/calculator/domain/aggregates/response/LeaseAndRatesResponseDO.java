package com.leasing.calculator.domain.aggregates.response;

import java.math.BigDecimal;

public record LeaseAndRatesResponseDO(
        String id,
        String make,
        String model,
        String modelVariant,
        String year,
        String fuelType,
        Double enginePower,
        Double engineSize,
        String url,
        String offer,
        Boolean terms,
        Boolean confirmation,
        BigDecimal carValue,
        int period,
        BigDecimal downPayment,
        int residualValuePercentage,
        Boolean isEcoFriendly,
        BigDecimal monthlyPayment
) {
}
