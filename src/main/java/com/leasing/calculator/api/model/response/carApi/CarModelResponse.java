package com.leasing.calculator.api.model.response.carApi;

import java.util.List;

public record CarModelResponse(
        List<CarModel> carModels
) {
    public record CarModel(
            int id,
            String name
    ) {
    }
}