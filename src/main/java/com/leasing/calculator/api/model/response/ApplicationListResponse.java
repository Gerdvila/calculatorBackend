package com.leasing.calculator.api.model.response;

import com.leasing.calculator.api.model.request.primitives.enums.LeaseApplicationStatus;

import java.time.LocalDateTime;

public record ApplicationListResponse(
        String id,
        String firstName,
        String lastName,
        boolean isOpened,
        LeaseApplicationStatus STATUS,
        LocalDateTime createdAt,
        boolean isHighRisk
) {
}