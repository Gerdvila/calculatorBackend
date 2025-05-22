package com.leasing.calculator.api.model.response;

public record HighRiskResponse(
        int currentMonthCount,
        int lastMonthCount
) {
}
