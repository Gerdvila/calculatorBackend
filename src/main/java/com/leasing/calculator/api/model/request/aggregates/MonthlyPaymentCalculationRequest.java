package com.leasing.calculator.api.model.request.aggregates;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Request object to calculate the monthly payment for a car.")
public record MonthlyPaymentCalculationRequest(

        @Schema(description = "The value of the car.", example = "30000.00")
        @NotNull
        BigDecimal carValue,

        @Schema(description = "The loan period in months.", example = "60")
        @NotNull
        int period,

        @Schema(description = "The down payment amount.", example = "5000.00")
        @NotNull
        BigDecimal downPayment,

        @Schema(description = "The percentage of the residual value of the car at the end of the period.", example = "20")
        @NotNull
        Integer residualValuePercentage,

        @Schema(description = "Indicates whether the car is eco-friendly or not.", example = "true")
        boolean isEcoFriendly
) {
}