package com.leasing.calculator.repository;

import com.leasing.calculator.domain.aggregates.response.InterestRateResponseDO;
import com.leasing.calculator.repository.mapper.InterestRateMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InterestRateRepositoryDAOImpl implements InterestRateRepositoryDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public InterestRateRepositoryDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public InterestRateResponseDO fetchAllInterestRate() {
        String query = """
                    SELECT regular, eco
                    FROM interest_rate
                """;
        return namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource(), new InterestRateMapper());
    }
}