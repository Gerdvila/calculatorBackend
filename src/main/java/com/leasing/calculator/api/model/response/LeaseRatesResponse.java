package com.leasing.calculator.api.model.response;

import java.math.BigDecimal;

public record LeaseRatesResponse(
        String id,
        BigDecimal carValue,
        int period,
        BigDecimal downPayment,
        long residualValuePercentage,
        Boolean isEcoFriendly,
        BigDecimal monthlyPayment
){
}