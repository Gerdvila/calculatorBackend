package com.leasing.calculator.api.model.response;

public record LeaseResponse(
        LeaseRatesResponse ratesResponse,
        PersonalInformationResponse personalInformationResponse,
        LeaseInformationResponse leaseResponse,
        LeaseStatusResponse statusResponse
) {
}