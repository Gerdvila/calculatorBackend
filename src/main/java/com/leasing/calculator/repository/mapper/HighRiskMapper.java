package com.leasing.calculator.repository.mapper;

import com.leasing.calculator.domain.aggregates.response.HighRiskResponseDO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HighRiskMapper implements RowMapper<HighRiskResponseDO> {
    @Override
    public HighRiskResponseDO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new HighRiskResponseDO(
                resultSet.getInt("currentMonthHighRiskCount"),
                resultSet.getInt("previousMonthHighRiskCount")
        );
    }
}
