package com.leasing.calculator.api.model.request.aggregates;

import com.leasing.calculator.api.model.request.primitives.enums.LeaseApplicationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LeaseStatusRequest(

        @Schema(description = "Unique identifier for the lease application", example = "9007199254740991")
        @NotNull
        @NotBlank
        String id,

        @Schema(description = "Application status", example = "ACCEPTED")
        @NotNull(message = "Application status cannot be null")
        @NotBlank(message = "Application status cannot be blank")
        LeaseApplicationStatus applicationStatus
) {
}