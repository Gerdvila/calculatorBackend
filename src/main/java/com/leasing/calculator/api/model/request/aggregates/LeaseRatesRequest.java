package com.leasing.calculator.api.model.request.aggregates;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record LeaseRatesRequest(

        @Schema(description = "Value of the car", example = "1000.00")
        BigDecimal carValue,

        @Schema(description = "Lease period in months", example = "12")
        int period,

        @Schema(description = "Initial down payment", example = "300.00")
        BigDecimal downPayment,

        @Schema(description = "Residual value percentage at the end of the lease", example = "15")
        int residualValuePercentage,

        @Schema(description = "Indicates if the car is eco-friendly", example = "true")
        Boolean isEcoFriendly,

        @Schema(description = "Monthly lease payment", example = "200.00")
        BigDecimal monthlyPayment

) {
}