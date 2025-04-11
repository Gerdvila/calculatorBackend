package com.leasing.calculator.service;

import com.leasing.calculator.api.model.response.LeaseResponse;
import com.leasing.calculator.domain.aggregates.response.LeaseAndRatesResponseDO;
import com.leasing.calculator.domain.aggregates.response.PersonalInformationResponseDO;
import com.leasing.calculator.domain.aggregates.response.StatusResponseDO;
import com.leasing.calculator.repository.LeaseAndRatesRepositoryDAO;
import com.leasing.calculator.repository.PersonalInformationRepositoryDAO;
import com.leasing.calculator.repository.StatusRepositoryDAO;
import com.leasing.calculator.util.MappingUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeasePaginationService {

    private final LeaseAndRatesRepositoryDAO leaseAndRatesRepository;
    private final PersonalInformationRepositoryDAO personalInformationRepository;
    private final StatusRepositoryDAO statusRepository;

    public LeasePaginationService(LeaseAndRatesRepositoryDAO leaseAndRatesRepository, PersonalInformationRepositoryDAO personalInformationRepository, StatusRepositoryDAO statusRepository) {
        this.leaseAndRatesRepository = leaseAndRatesRepository;
        this.personalInformationRepository = personalInformationRepository;
        this.statusRepository = statusRepository;
    }

    public List<LeaseResponse> selectAllApplicationsByPage(long id) {
        List<LeaseAndRatesResponseDO> leaseAndRatesDAOResponses = leaseAndRatesRepository.getAllLeaseAndRatesByPage(id);
        List<PersonalInformationResponseDO> personalInformationDAOResponses = personalInformationRepository.getAllPersonalInformationByPage(id);
        List<StatusResponseDO> statusDaoResponses = statusRepository.getAllStatusByPage(id);
        return leaseAndRatesDAOResponses.stream()
                .map(leaseAndRatesDAOResponse -> new LeaseResponse(
                        MappingUtil.convertDOResponseIntoRatesResponse(leaseAndRatesDAOResponse),
                        MappingUtil.convertDOResponseIntoPersonalInformationResponse(personalInformationDAOResponses
                                .get(leaseAndRatesDAOResponses.indexOf(leaseAndRatesDAOResponse))),
                        MappingUtil.convertDOResponseIntoLeaseInformationResponse(leaseAndRatesDAOResponse),
                        MappingUtil.convertDOResponseIntoStatusResponse(statusDaoResponses
                                .get(leaseAndRatesDAOResponses.indexOf(leaseAndRatesDAOResponse))))).toList();

    }
}
