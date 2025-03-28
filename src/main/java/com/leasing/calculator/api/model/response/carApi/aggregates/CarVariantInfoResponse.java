package com.leasing.calculator.api.model.response.carApi.aggregates;

import java.util.List;

public record CarVariantInfoResponse(
        List<Integer> years,
        List<String> fuelTypes,
        List<Integer> enginePowers,
        List<String> engineSizes
) {
}