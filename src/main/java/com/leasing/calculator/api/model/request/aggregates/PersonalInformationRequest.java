package com.leasing.calculator.api.model.request.aggregates;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PersonalInformationRequest(

        @Schema(description = "First name of the applicant", example = "John")
        String firstName,

        @Schema(description = "Last name of the applicant", example = "Doe")
        String lastName,

        @Schema(description = "Email address", example = "john.doe@example.com")
        String email,

        @Schema(description = "Contact phone number", example = "+37060490224")
        String phoneNumber,

        @Schema(description = "Personal identification number", example = "50304231429")
        String pid,

        @Schema(description = "Date of birth", example = "1990-05-15T10:00:00.000Z")
        LocalDateTime dateOfBirth,

        @Schema(description = "Marital status", example = "Single")
        String maritalStatus,

        @Schema(description = "Number of children", example = "2")
        int numberOfChildren,

        @Schema(description = "Citizenship country", example = "Austria")
        String citizenship,

        @Schema(description = "Monthly income in EUR", example = "5000.00")
        BigDecimal monthlyIncome,

        @Schema(description = "Preferred language", example = "en")
        String languagePref
) {
}