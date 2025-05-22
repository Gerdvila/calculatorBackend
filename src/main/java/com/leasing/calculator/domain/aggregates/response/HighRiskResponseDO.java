package com.leasing.calculator.domain.aggregates.response;

public record HighRiskResponseDO(
        int currentMonthCount,
        int lastMonthCount
) {
}