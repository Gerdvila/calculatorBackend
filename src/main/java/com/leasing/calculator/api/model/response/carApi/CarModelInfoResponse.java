package com.leasing.calculator.api.model.response.carApi;

import java.util.List;

public record CarModelInfoResponse(
        List<Variant> variants,
        List<Integer> years,
        List<String> fuelTypes,
        List<Integer> enginePowers,
        List<String> engineSizes
) {

    public record Variant(
            int id,
            String name
    ) {
    }
}