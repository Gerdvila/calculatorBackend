package com.leasing.calculator.repository;

import com.leasing.calculator.api.model.request.aggregates.UserRegisterRequest;
import com.leasing.calculator.domain.aggregates.response.UserCredentialResponseDO;

import java.util.Optional;

public interface UserAuthRepositoryDAO {
    Optional<UserCredentialResponseDO> getUserByLogin(String login);

    UserCredentialResponseDO registerUser(UserRegisterRequest userRegisterRequest);
}