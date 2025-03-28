package com.leasing.calculator.repository;

import com.leasing.calculator.domain.aggregates.request.PersonalInformationRequestDO;

public interface PersonalInformationRepositoryDAO {
    String createPersonalInformation(PersonalInformationRequestDO personalInformationDAORequest);
}
