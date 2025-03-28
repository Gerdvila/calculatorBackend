package com.leasing.calculator.domain.aggregates.response.carApi;

import java.util.List;
import java.util.Map;

public record CarModelResponseDO(
        Map<String, Object> collection,
        List<APIResponseModelData> data
) {
    public record APIResponseModelData(
            int id,
            int make_id,
            String name
    ) {
    }
}
