package com.leasing.calculator.api.model.response;

public record FetchLeaseByIdResponse(
        LeaseRatesResponse ratesResponse,
        PersonalInformationResponse personalInformationResponse,
        LeaseInformationResponse leaseResponse
) {
}