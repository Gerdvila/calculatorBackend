package com.leasing.calculator.util;

import com.leasing.calculator.api.model.request.aggregates.FetchLeaseListBySearchQueryRequest;
import com.leasing.calculator.api.model.request.primitives.enums.LeaseApplicationStatus;
import com.leasing.calculator.api.model.response.ApplicationListResponse;
import com.leasing.calculator.api.model.response.LeaseInformationResponse;
import com.leasing.calculator.api.model.response.LeaseRatesResponse;
import com.leasing.calculator.api.model.response.LeaseStatusResponse;
import com.leasing.calculator.api.model.response.PersonalInformationResponse;
import com.leasing.calculator.domain.aggregates.request.FetchLeaseBySearchQueryRequestDO;
import com.leasing.calculator.domain.aggregates.response.ApplicationListResponseDO;
import com.leasing.calculator.domain.aggregates.response.LeaseAndRatesResponseDO;
import com.leasing.calculator.domain.aggregates.response.PersonalInformationResponseDO;
import com.leasing.calculator.domain.aggregates.response.StatusResponseDO;

public class MappingUtil {

    private MappingUtil() {}

    public static LeaseInformationResponse convertDOResponseIntoLeaseInformationResponse(LeaseAndRatesResponseDO leaseAndRatesResponseDO) {

        return new LeaseInformationResponse(
                leaseAndRatesResponseDO.id(),
                leaseAndRatesResponseDO.make(),
                leaseAndRatesResponseDO.model(),
                leaseAndRatesResponseDO.modelVariant(),
                leaseAndRatesResponseDO.year(),
                leaseAndRatesResponseDO.fuelType(),
                leaseAndRatesResponseDO.enginePower(),
                leaseAndRatesResponseDO.engineSize(),
                leaseAndRatesResponseDO.url(),
                leaseAndRatesResponseDO.offer(),
                leaseAndRatesResponseDO.terms(),
                leaseAndRatesResponseDO.confirmation()
        );
    }

    public static LeaseRatesResponse convertDOResponseIntoRatesResponse(LeaseAndRatesResponseDO leaseAndRatesResponseDO) {
        return new LeaseRatesResponse(
                leaseAndRatesResponseDO.id(),
                leaseAndRatesResponseDO.carValue(),
                leaseAndRatesResponseDO.period(),
                leaseAndRatesResponseDO.downPayment(),
                leaseAndRatesResponseDO.residualValuePercentage(),
                leaseAndRatesResponseDO.isEcoFriendly(),
                leaseAndRatesResponseDO.monthlyPayment()
        );
    }

    public static PersonalInformationResponse convertDOResponseIntoPersonalInformationResponse(PersonalInformationResponseDO personalInformationResponseDO) {
        return new PersonalInformationResponse(
                personalInformationResponseDO.id(),
                personalInformationResponseDO.firstName(),
                personalInformationResponseDO.lastName(),
                personalInformationResponseDO.email(),
                personalInformationResponseDO.phoneNumber(),
                personalInformationResponseDO.pid(),
                personalInformationResponseDO.dateOfBirth(),
                personalInformationResponseDO.maritalStatus(),
                personalInformationResponseDO.numberOfChildren(),
                personalInformationResponseDO.citizenship(),
                personalInformationResponseDO.monthlyIncome(),
                personalInformationResponseDO.languagePref());
    }

    public static LeaseStatusResponse convertDOResponseIntoStatusResponse(StatusResponseDO statusResponseDO) {
        return new LeaseStatusResponse(
                statusResponseDO.id(),
                statusResponseDO.status(),
                statusResponseDO.isOpened(),
                statusResponseDO.updatedAt(),
                statusResponseDO.createdAt(),
                statusResponseDO.isHighRisk()
        );
    }

    public static ApplicationListResponse convertApplicationListDAOResponseIntoResponse(ApplicationListResponseDO applicationListResponseDO) {
        return new ApplicationListResponse(
                applicationListResponseDO.id(),
                applicationListResponseDO.firstName(),
                applicationListResponseDO.lastName(),
                applicationListResponseDO.isOpened(),
                applicationListResponseDO.status(),
                applicationListResponseDO.createdAt(),
                applicationListResponseDO.isHighRisk()
        );
    }

    public static FetchLeaseBySearchQueryRequestDO convertRequestIntoDORequestFetchLeaseBySearchQuery(FetchLeaseListBySearchQueryRequest applicationListRequest) {
        if (null != applicationListRequest.searchQuery() && null != applicationListRequest.STATUS()) {
            return new FetchLeaseBySearchQueryRequestDO(
                    applicationListRequest.page(),
                    applicationListRequest.STATUS().stream().map(LeaseApplicationStatus::toString).toList(),
                    applicationListRequest.searchQuery());
        } else if (null != applicationListRequest.STATUS()) {
            return new FetchLeaseBySearchQueryRequestDO(
                    applicationListRequest.page(),
                    applicationListRequest.STATUS().stream().map(LeaseApplicationStatus::toString).toList(),
                    null);
        } else {
            return new FetchLeaseBySearchQueryRequestDO(
                    applicationListRequest.page(),
                    null,
                    applicationListRequest.searchQuery());
        }
    }
}