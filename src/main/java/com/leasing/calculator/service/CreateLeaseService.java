package com.leasing.calculator.service;

import com.leasing.calculator.api.model.request.aggregates.CreateLeaseApplicationRequest;
import com.leasing.calculator.api.model.request.aggregates.LeaseInformationRequest;
import com.leasing.calculator.api.model.request.aggregates.LeaseRatesRequest;
import com.leasing.calculator.domain.aggregates.request.CreateLeaseAndRatesRequestDO;
import com.leasing.calculator.domain.aggregates.request.PersonalInformationRequestDO;
import com.leasing.calculator.repository.LeaseAndRatesRepositoryDAO;
import com.leasing.calculator.repository.PersonalInformationRepositoryDAO;
import com.leasing.calculator.repository.StatusRepositoryDAO;
import com.leasing.calculator.util.LeaseValidationUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateLeaseService {

    private final LeaseAndRatesRepositoryDAO leaseAndRatesRepositoryDAO;
    private final PersonalInformationRepositoryDAO personalInformationRepositoryDAO;
    private final StatusRepositoryDAO statusRepositoryDAO;

    public CreateLeaseService(LeaseAndRatesRepositoryDAO leaseAndRatesRepositoryDAO, PersonalInformationRepositoryDAO personalInformationRepository, StatusRepositoryDAO statusRepositoryDAO) {
        this.leaseAndRatesRepositoryDAO = leaseAndRatesRepositoryDAO;
        this.personalInformationRepositoryDAO = personalInformationRepository;
        this.statusRepositoryDAO = statusRepositoryDAO;
    }

    @Transactional
    public void createApplication(CreateLeaseApplicationRequest createLeaseApplicationRequest) {
        PersonalInformationRequestDO personalInformationDAORequest = convertCreateLeaseApplicationRequestIntoPersonalInformationRequest(createLeaseApplicationRequest);
        CreateLeaseAndRatesRequestDO leaseAndRatesDAORequest = convertCreateLeaseRequestToDO(createLeaseApplicationRequest);
        try {
            LeaseValidationUtil.validatePersonalInformation(personalInformationDAORequest);
            LeaseValidationUtil.validateLeaseAndRatesResponse(leaseAndRatesDAORequest);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        boolean isHighRisk = LeaseValidationUtil.validateIfHighRisk(personalInformationDAORequest, leaseAndRatesDAORequest);
        String pid = personalInformationRepositoryDAO.createPersonalInformation(personalInformationDAORequest);
        if (pid != null) {
            leaseAndRatesRepositoryDAO.createLeaseAndRate(leaseAndRatesDAORequest, pid);
            statusRepositoryDAO.createStatus(pid, isHighRisk);
        }
    }

    private PersonalInformationRequestDO convertCreateLeaseApplicationRequestIntoPersonalInformationRequest(CreateLeaseApplicationRequest createLeaseApplicationRequest) {
        return new PersonalInformationRequestDO.Builder()
                .withFirstName(createLeaseApplicationRequest.personalInformationRequest().firstName())
                .withLastName(createLeaseApplicationRequest.personalInformationRequest().lastName())
                .withEmail(createLeaseApplicationRequest.personalInformationRequest().email())
                .withPhoneNumber(createLeaseApplicationRequest.personalInformationRequest().phoneNumber())
                .withPid(createLeaseApplicationRequest.personalInformationRequest().pid())
                .withDateOfBirth(createLeaseApplicationRequest.personalInformationRequest().dateOfBirth())
                .withMaritalStatus(createLeaseApplicationRequest.personalInformationRequest().maritalStatus())
                .withNumberOfChildren(createLeaseApplicationRequest.personalInformationRequest().numberOfChildren())
                .withCitizenship(createLeaseApplicationRequest.personalInformationRequest().citizenship())
                .withMonthlyIncome(createLeaseApplicationRequest.personalInformationRequest().monthlyIncome())
                .withLanguagePref(createLeaseApplicationRequest.personalInformationRequest().languagePref())
                .build();
    }

    private CreateLeaseAndRatesRequestDO convertCreateLeaseRequestToDO(CreateLeaseApplicationRequest createLeaseApplicationRequest) {
        LeaseInformationRequest leaseRequest = createLeaseApplicationRequest.leaseRequest();
        LeaseRatesRequest leaseRatesRequest = createLeaseApplicationRequest.leaseRatesRequest();

        return new CreateLeaseAndRatesRequestDO.Builder()
                .withMake(leaseRequest.make())
                .withModel(leaseRequest.model())
                .withModelVariant(leaseRequest.modelVariant())
                .withYear(leaseRequest.year())
                .withFuelType(leaseRequest.fuelType())
                .withEnginePower(leaseRequest.enginePower())
                .withEngineSize(leaseRequest.engineSize())
                .withUrl(leaseRequest.url())
                .withOffer(leaseRequest.offer())
                .withTerms(leaseRequest.terms())
                .withConfirmation(leaseRequest.confirmation())
                .withCarValue(leaseRatesRequest.carValue())
                .withPeriod(leaseRatesRequest.period())
                .withDownPayment(leaseRatesRequest.downPayment())
                .withResidualValuePercentage(leaseRatesRequest.residualValuePercentage())
                .withIsEcoFriendly(leaseRatesRequest.isEcoFriendly())
                .withMonthlyPayment(leaseRatesRequest.monthlyPayment())
                .build();
    }
}