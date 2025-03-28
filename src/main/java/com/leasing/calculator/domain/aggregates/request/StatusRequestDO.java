package com.leasing.calculator.domain.aggregates.request;

import com.leasing.calculator.api.model.request.primitives.enums.LeaseApplicationStatus;

public record StatusRequestDO(
        String id,
        LeaseApplicationStatus applicationStatus
) {
}
