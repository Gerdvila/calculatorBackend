package com.leasing.calculator.repository;

import com.leasing.calculator.repository.mapper.CarAPIJwtMapper;
import com.leasing.calculator.util.CarAPIJwt;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class CarApiJwtRepositoryDAOImpl implements CarApiJwtRepositoryDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CarApiJwtRepositoryDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public CarAPIJwt getJwtToken() {
        String query = "SELECT jwt, expires_at FROM jwt_token";
        return namedParameterJdbcTemplate.queryForObject(query, new HashMap<>(), new CarAPIJwtMapper());
    }

    public void updateJwtToken(CarAPIJwt jwtToken) {
        String query = "UPDATE jwt_token SET jwt = :jwt, expires_at = :expiresAt";
        namedParameterJdbcTemplate.update(query, new BeanPropertySqlParameterSource(jwtToken));
    }
}