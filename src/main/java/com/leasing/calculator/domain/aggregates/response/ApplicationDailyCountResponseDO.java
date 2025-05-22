package com.leasing.calculator.domain.aggregates.response;

import java.time.LocalDateTime;

public record ApplicationDailyCountResponseDO(
        LocalDateTime day,
        int applicationCount
) {
}