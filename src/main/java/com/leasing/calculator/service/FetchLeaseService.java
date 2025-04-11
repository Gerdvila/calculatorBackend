package com.leasing.calculator.service;

import com.leasing.calculator.api.model.exceptions.ApplicationNotFoundException;
import com.leasing.calculator.api.model.response.FetchLeaseByIdResponse;
import com.leasing.calculator.api.model.response.LeaseInformationResponse;
import com.leasing.calculator.api.model.response.LeaseRatesResponse;
import com.leasing.calculator.api.model.response.LeaseResponse;
import com.leasing.calculator.api.model.response.PersonalInformationResponse;
import com.leasing.calculator.domain.aggregates.response.LeaseAndRatesResponseDO;
import com.leasing.calculator.domain.aggregates.response.PersonalInformationResponseDO;
import com.leasing.calculator.repository.LeaseAndRatesRepositoryDAO;
import com.leasing.calculator.repository.PersonalInformationRepositoryDAO;
import com.leasing.calculator.util.MappingUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FetchLeaseService {

    private final LeaseAndRatesRepositoryDAO leaseAndRatesRepository;
    private final PersonalInformationRepositoryDAO personalInformationRepository;

    public FetchLeaseService(LeaseAndRatesRepositoryDAO leaseAndRatesRepository, PersonalInformationRepositoryDAO personalInformationRepository) {
        this.leaseAndRatesRepository = leaseAndRatesRepository;
        this.personalInformationRepository = personalInformationRepository;
    }

    public FetchLeaseByIdResponse getApplicationById(String id) throws ApplicationNotFoundException {

        Optional<LeaseAndRatesResponseDO> leaseAndRatesResponseDO = leaseAndRatesRepository.getLeaseAndRateById(id);
        Optional<PersonalInformationResponseDO> personalInformationDAOResponse = personalInformationRepository.getPersonalInformationById(id);

        if (leaseAndRatesResponseDO.isPresent() && personalInformationDAOResponse.isPresent()) {

            LeaseInformationResponse leaseResponse = MappingUtil.convertDOResponseIntoLeaseInformationResponse(leaseAndRatesResponseDO.orElse(null));
            LeaseRatesResponse ratesResponse = MappingUtil.convertDOResponseIntoRatesResponse(leaseAndRatesResponseDO.orElse(null));
            PersonalInformationResponse personalInformationResponse = MappingUtil.convertDOResponseIntoPersonalInformationResponse(personalInformationDAOResponse.orElse(null));

            return new FetchLeaseByIdResponse(ratesResponse, personalInformationResponse, leaseResponse);
        }
        throw new ApplicationNotFoundException(id);
    }
}