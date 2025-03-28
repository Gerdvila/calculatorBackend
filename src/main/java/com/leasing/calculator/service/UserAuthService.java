package com.leasing.calculator.service;

import com.leasing.calculator.api.model.exceptions.AppException;
import com.leasing.calculator.api.model.request.UserLoginRequest;
import com.leasing.calculator.api.model.request.UserRegisterRequest;
import com.leasing.calculator.api.model.response.UserCredentialResponse;
import com.leasing.calculator.domain.aggregates.response.UserCredentialResponseDO;
import com.leasing.calculator.repository.UserAuthRepositoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    UserAuthRepositoryDAO userAuthRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserAuthService(UserAuthRepositoryDAO userAuthRepository, PasswordEncoder passwordEncoder) {
        this.userAuthRepository = userAuthRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserCredentialResponse login(UserLoginRequest userLoginRequest) {
        UserCredentialResponseDO userCredentialDAOResponse = userAuthRepository.getUserByLogin(userLoginRequest.login())
                .orElseThrow(() -> new AppException("Unknown user ", HttpStatus.IM_USED));

        if (passwordEncoder.matches(userLoginRequest.password(), userCredentialDAOResponse.getPassword())) {
            return convertUserCredentialDAOResponseToUserCredentialResponse(userCredentialDAOResponse);
        }
        throw new AppException("Unknown user ", HttpStatus.IM_USED);

    }

    public UserCredentialResponse register(UserRegisterRequest userCredentials) {
            return convertUserCredentialDAOResponseToUserCredentialResponse(userAuthRepository.registerUser(userCredentials));
    }

    private UserCredentialResponse convertUserCredentialDAOResponseToUserCredentialResponse(
            UserCredentialResponseDO userCredentialDAOResponse) {
        return new UserCredentialResponse.Builder()
                .withId(userCredentialDAOResponse.getId())
                .withEmail(userCredentialDAOResponse.getEmail())
                .withFirstName(userCredentialDAOResponse.getFirstName())
                .withLastName(userCredentialDAOResponse.getLastName())
                .withLogin(userCredentialDAOResponse.getLogin())
                .withPhone(userCredentialDAOResponse.getPhone())
                .withRole(userCredentialDAOResponse.getRole())
                .withPassword(userCredentialDAOResponse.getPassword()).build();
    }
}