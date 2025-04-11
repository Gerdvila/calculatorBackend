package com.leasing.calculator.api.model.response;

public record LeaseInformationResponse(
        String id,
        String make,
        String model,
        String modelVariant,
        String year,
        String fuelType,
        Double enginePower,
        Double engineSize,
        String url,
        String offer,
        Boolean terms,
        Boolean confirmation
) {
}
