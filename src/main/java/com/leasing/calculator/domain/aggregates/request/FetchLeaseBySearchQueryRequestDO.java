package com.leasing.calculator.domain.aggregates.request;

import java.util.List;

public record FetchLeaseBySearchQueryRequestDO(
        long page,
        List<String> statuses,
        String searchQuery
) {
}