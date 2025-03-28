package com.leasing.calculator.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record MonthlyPaymentCalculationResponse(
        @Schema(example = "9000.23", description = "Monthly payment for specified value of car.")
        BigDecimal monthlyPayment
){}