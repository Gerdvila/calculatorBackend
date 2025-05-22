package com.leasing.calculator.repository.mapper;

import com.leasing.calculator.domain.aggregates.response.ApplicationMonthlyCountResponseDO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationMonthlyCountMapper implements RowMapper<ApplicationMonthlyCountResponseDO> {
    @Override
    public ApplicationMonthlyCountResponseDO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new ApplicationMonthlyCountResponseDO(
                resultSet.getInt("thisMonthCount"),
                resultSet.getInt("previousMonthCount")
        );
    }
}
