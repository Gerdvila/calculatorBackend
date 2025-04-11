package com.leasing.calculator.api.model.response;

import com.leasing.calculator.api.model.request.primitives.enums.LeaseApplicationStatus;

import java.time.LocalDateTime;

public record LeaseStatusResponse(
        String id,

        LeaseApplicationStatus APPLICATIONSTATUS,

        Boolean isOpened,

        LocalDateTime updatedAt,

        LocalDateTime createdAt,

        Boolean isHighRisk
) {
}