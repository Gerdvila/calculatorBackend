package com.leasing.calculator.domain.aggregates.response;

import java.time.LocalDateTime;

public record MailResponseDO(
        String id,
        String applicationId,
        String mailText,
        LocalDateTime createdAt
) {
}