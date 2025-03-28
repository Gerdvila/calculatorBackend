package com.leasing.calculator.api.model.request.aggregates;

public record CreateLeaseApplicationRequest(
        LeaseRatesRequest leaseRatesRequest,
        PersonalInformationRequest personalInformationRequest,
        LeaseInformationRequest leaseRequest,
        LeaseStatusRequest leaseStatusRequest
) {
}
