package com.leasing.calculator.api.model.response.carApi.aggregates;

import java.util.List;

public record CarMakeResponse(
        List<String> carMakes
) {
}