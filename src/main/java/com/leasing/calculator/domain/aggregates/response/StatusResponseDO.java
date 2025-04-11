package com.leasing.calculator.domain.aggregates.response;

import com.leasing.calculator.api.model.request.primitives.enums.LeaseApplicationStatus;

import java.time.LocalDateTime;

public record StatusResponseDO(
        String id,
        LeaseApplicationStatus status,
        Boolean isOpened,
        LocalDateTime updatedAt,
        LocalDateTime createdAt,
        Boolean isHighRisk
) {
}
