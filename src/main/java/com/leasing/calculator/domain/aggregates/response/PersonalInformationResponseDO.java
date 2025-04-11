package com.leasing.calculator.domain.aggregates.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PersonalInformationResponseDO(
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
