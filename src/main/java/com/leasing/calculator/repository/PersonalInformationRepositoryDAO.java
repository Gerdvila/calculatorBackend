package com.leasing.calculator.repository;

import com.leasing.calculator.domain.aggregates.request.PersonalInformationRequestDO;
import com.leasing.calculator.domain.aggregates.response.PersonalInformationResponseDO;

import java.util.List;
import java.util.Optional;

public interface PersonalInformationRepositoryDAO {
    String createPersonalInformation(PersonalInformationRequestDO personalInformationDAORequest);

    List<PersonalInformationResponseDO> getAllPersonalInformationByPage(long pageNumber);

    Optional<PersonalInformationResponseDO> getPersonalInformationById(String pid);
}
