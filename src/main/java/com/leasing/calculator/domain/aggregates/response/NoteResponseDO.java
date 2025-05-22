package com.leasing.calculator.domain.aggregates.response;

import java.time.LocalDateTime;

public record NoteResponseDO(
        String id,
        String applicationId,
        String noteText,
        LocalDateTime createdAt
) {
}