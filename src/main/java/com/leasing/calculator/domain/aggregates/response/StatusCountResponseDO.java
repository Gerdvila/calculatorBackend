package com.leasing.calculator.domain.aggregates.response;

public record StatusCountResponseDO(
        int newCount,
        int acceptedCount,
        int rejectedCount,
        int pendingCount
){
}