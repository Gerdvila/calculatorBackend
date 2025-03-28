package com.leasing.calculator.api.controller;

import com.leasing.calculator.api.model.request.UserLoginRequest;
import com.leasing.calculator.api.model.request.UserRegisterRequest;
import com.leasing.calculator.api.model.response.UserCredentialResponse;
import com.leasing.calculator.config.security.UserAuthProvider;
import com.leasing.calculator.service.UserAuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Manage session-related functionalities such as user login and registration.")
public class UserAuthController {

    private final UserAuthService userAuthService;
    private final UserAuthProvider userAuthProvider;

    public UserAuthController(UserAuthService userAuthService, UserAuthProvider userAuthProvider) {
        this.userAuthService = userAuthService;
        this.userAuthProvider = userAuthProvider;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserCredentialResponse login(@RequestBody UserLoginRequest userLoginRequest) {
        UserCredentialResponse user = userAuthService.login(userLoginRequest);
        user.setToken(userAuthProvider.createToken(user));
        return user;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserCredentialResponse register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        System.out.println("Database Port: " + System.getenv("DATABASE_PORT"));
        UserCredentialResponse userCredentialResponse = userAuthService.register(userRegisterRequest);
        userCredentialResponse.setToken(userAuthProvider.createToken(userCredentialResponse));
        return userCredentialResponse;
    }
}