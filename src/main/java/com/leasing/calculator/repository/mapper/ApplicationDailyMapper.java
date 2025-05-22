package com.leasing.calculator.repository.mapper;

import com.leasing.calculator.domain.aggregates.response.ApplicationDailyCountResponseDO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationDailyMapper implements RowMapper<ApplicationDailyCountResponseDO> {
    @Override
    public ApplicationDailyCountResponseDO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new ApplicationDailyCountResponseDO(
                resultSet.getTimestamp("day").toLocalDateTime(),
                resultSet.getInt("applicationCount")
        );
    }
}
