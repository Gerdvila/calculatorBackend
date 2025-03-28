package com.leasing.calculator.repository.mapper;

import com.leasing.calculator.domain.aggregates.response.InterestRateResponseDO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InterestRateMapper implements RowMapper<InterestRateResponseDO> {
    @Override
    public InterestRateResponseDO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new InterestRateResponseDO(
                resultSet.getBigDecimal("regular"),
                resultSet.getBigDecimal("eco")
        );
    }
}
