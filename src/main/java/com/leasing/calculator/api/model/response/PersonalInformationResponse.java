package com.leasing.calculator.api.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PersonalInformationResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String pid,
        LocalDateTime dateOfBirth,
        String maritalStatus,
        int numberOfChildren,
        String citizenship,
        BigDecimal monthlyIncome,
        String languagePref
) {
}