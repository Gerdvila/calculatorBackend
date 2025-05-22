package com.leasing.calculator.api.model.response;

import java.time.LocalDateTime;

public record ApplicationDailyCountResponse(
       LocalDateTime day,
       int applicationCount
) {
}
