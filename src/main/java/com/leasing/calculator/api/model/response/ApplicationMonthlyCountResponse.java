package com.leasing.calculator.api.model.response;

public record ApplicationMonthlyCountResponse(
        int thisMonthCount,
        int previousMonthCount
) {
}
