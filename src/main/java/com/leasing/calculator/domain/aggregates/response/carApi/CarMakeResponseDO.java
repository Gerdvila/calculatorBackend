package com.leasing.calculator.domain.aggregates.response.carApi;

import java.util.List;
import java.util.Map;

public record CarMakeResponseDO(
        Map<String, Object> collection,
        List<Map<String, Object>> data
) {
}
