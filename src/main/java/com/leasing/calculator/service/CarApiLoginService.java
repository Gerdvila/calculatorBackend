package com.leasing.calculator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leasing.calculator.repository.CarApiJwtRepositoryDAO;
import com.leasing.calculator.util.CarAPIJwt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;

@Service
public class CarApiLoginService {

    private final CarApiJwtRepositoryDAO jwtRepository;

    @Value("${car.api.token}")
    private String carApiToken;

    @Value("${car.api.secret}")
    private String carApiSecret;

    public CarApiLoginService(CarApiJwtRepositoryDAO jwtRepository) {
        this.jwtRepository = jwtRepository;
    }

    public CarAPIJwt loginAndSetJwt() throws JsonProcessingException {

        String jwt = jwtRepository.fetchJwtFromCarAPI(carApiToken,carApiSecret);
        int expiresAt = getExpiresAt(jwt);
        CarAPIJwt updatedJwt = new CarAPIJwt(jwt, expiresAt);

        jwtRepository.updateJwtToken(updatedJwt);
        return updatedJwt;
    }

    private static int getExpiresAt(String jwt) throws JsonProcessingException {
        String[] parts = jwt.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid JWT token");
        }

        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> claimsMap = objectMapper.readValue(payload, new TypeReference<>() {});

        return (int) claimsMap.get("exp");
    }
}