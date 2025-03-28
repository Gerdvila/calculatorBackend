package com.leasing.calculator.api.model.request.aggregates;

import io.swagger.v3.oas.annotations.media.Schema;

public record LeaseInformationRequest(

        @Schema(description = "Make of the car", example = "Tesla")
        String make,

        @Schema(description = "Model of the car", example = "Model 3")
        String model,

        @Schema(description = "Variant of the car model", example = "Long Range")
        String modelVariant,

        @Schema(description = "Manufacturing year", example = "2019")
        String year,

        @Schema(description = "Type of fuel used", example = "Electric")
        String fuelType,

        @Schema(description = "Engine power in kW", example = "258.0")
        Double enginePower,

        @Schema(description = "Engine size in liters", example = "0.0")
        Double engineSize,

        @Schema(description = "URL to the car's listing", example = "https://example.com/car-listing")
        String url,

        @Schema(description = "Special offer details", example = "0% interest for 12 months")
        String offer,

        @Schema(description = "Acceptance of terms and conditions", example = "true")
        Boolean terms,

        @Schema(description = "Confirmation of the lease request", example = "true")
        Boolean confirmation

    ) {
    }