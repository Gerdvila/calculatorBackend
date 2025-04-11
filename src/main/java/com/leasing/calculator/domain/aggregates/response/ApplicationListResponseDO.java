package com.leasing.calculator.domain.aggregates.response;

import com.leasing.calculator.api.model.request.primitives.enums.LeaseApplicationStatus;

import java.time.LocalDateTime;

public record ApplicationListResponseDO(
        String id,
        String firstName,
        String lastName,
        boolean isOpened,
        LeaseApplicationStatus status,
        LocalDateTime createdAt,
        boolean isHighRisk
) {
}