package com.leasing.calculator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leasing.calculator.repository.CarApiJwtRepositoryDAO;
import com.leasing.calculator.util.CarAPIJwt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.auth0.jwt.JWT.decode;

@Service
public class CarApiLoginService {

    private final RestTemplate restTemplate;
    private final CarApiJwtRepositoryDAO jwtRepository;

    @Value("${car.api.token}")
    private String carApiToken;

    @Value("${car.api.secret}")
    private String carApiSecret;

    private static final String CAR_API_LOGIN_URL = "https://carapi.app/api/auth/login";



    public CarApiLoginService(RestTemplate restTemplate, CarApiJwtRepositoryDAO jwtRepository) {
        this.restTemplate = restTemplate;
        this.jwtRepository = jwtRepository;
    }

    public CarAPIJwt loginAndSetJwt() throws JsonProcessingException {

        Map<String, String> loginRequestBody = new HashMap<>();
        loginRequestBody.put("api_token", carApiToken);
        loginRequestBody.put("api_secret", carApiSecret);

        ResponseEntity<String> loginResponse = restTemplate.postForEntity(
                CAR_API_LOGIN_URL,
                loginRequestBody,
                String.class
        );

        String jwt = loginResponse.getBody();
        int expiresAt = getExpiresAt(jwt);
        CarAPIJwt updatedJwt = new CarAPIJwt(jwt, expiresAt);

        jwtRepository.updateJwtToken(updatedJwt);
        return updatedJwt;
    }

    private static int getExpiresAt(String jwt) throws JsonProcessingException {
        String claims = decode(jwt).getClaims().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> claimsMap = objectMapper.readValue(claims, new TypeReference<>() {
        });
        return (int) claimsMap.get("exp");
    }
}