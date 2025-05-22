package com.leasing.calculator.repository;

import com.leasing.calculator.repository.mapper.CarAPIJwtMapper;
import com.leasing.calculator.util.CarAPIJwt;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;

@Repository
public class CarApiJwtRepositoryDAOImpl implements CarApiJwtRepositoryDAO {

    private final RestTemplate restTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CarApiJwtRepositoryDAOImpl(RestTemplate restTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.restTemplate = restTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public CarAPIJwt getJwtToken() {
        try {
            String query = "SELECT jwt, expires_at FROM jwt_token";
            return namedParameterJdbcTemplate.queryForObject(query, new HashMap<>(), new CarAPIJwtMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void updateJwtToken(CarAPIJwt jwtToken) {
        String query = """
                INSERT INTO jwt_token (id, jwt, expires_at)
                VALUES (1, :jwt, :expiresAt)
                ON CONFLICT (id)
                DO UPDATE SET jwt = EXCLUDED.jwt, expires_at = EXCLUDED.expires_at
                """;
        namedParameterJdbcTemplate.update(query, new BeanPropertySqlParameterSource(jwtToken));
    }

    @Override
    public String fetchJwtFromCarAPI(String carApiToken, String carApiSecret) {
        String jsonBody = """
                {
                    "api_token": "%s",
                    "api_secret": "%s"
                }
                """.formatted(carApiToken, carApiSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        return restTemplate.postForObject(
                "https://carapi.app/api/auth/login",
                entity,
                String.class
        );
    }
}