package com.leasing.calculator.api.model.request.aggregates;

import com.leasing.calculator.api.model.request.primitives.enums.LeaseApplicationStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FetchLeaseListBySearchQueryRequest(
        @NotNull(message = "Page number is required")
        @Min(value = 1, message = "Page number must be greater than 0")
        Long page,
        List<LeaseApplicationStatus> STATUS,
        String searchQuery
) {
}
