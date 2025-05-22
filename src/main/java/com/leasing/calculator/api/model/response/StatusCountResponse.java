package com.leasing.calculator.api.model.response;

public record StatusCountResponse(
        int newCount,
        int acceptedCount,
        int rejectedCount,
        int pendingCount
) {

}
