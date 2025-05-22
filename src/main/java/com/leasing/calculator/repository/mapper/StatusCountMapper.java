package com.leasing.calculator.repository.mapper;

import com.leasing.calculator.domain.aggregates.response.StatusCountResponseDO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusCountMapper implements RowMapper<StatusCountResponseDO> {
    @Override
    public StatusCountResponseDO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new StatusCountResponseDO(
                resultSet.getInt("newCount"),
                resultSet.getInt("acceptedCount"),
                resultSet.getInt("rejectedCount"),
                resultSet.getInt("pendingCount")
        );
    }
}
