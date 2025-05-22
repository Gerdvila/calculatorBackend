package com.leasing.calculator.repository.mapper;

import com.leasing.calculator.domain.aggregates.response.AcceptedApplicationResponseDO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AcceptedApplicationMapper implements RowMapper<AcceptedApplicationResponseDO> {
    @Override
    public AcceptedApplicationResponseDO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new AcceptedApplicationResponseDO(
                resultSet.getBigDecimal("thisYearTotalPayments"),
                resultSet.getBigDecimal("lastYearTotalPayments")
        );
    }
}

