package com.leasing.calculator.repository.mapper;

import com.leasing.calculator.api.model.request.primitives.enums.LeaseApplicationStatus;
import com.leasing.calculator.domain.aggregates.response.StatusResponseDO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class StatusMapper implements RowMapper<StatusResponseDO> {
    @Override
    public StatusResponseDO mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        return new StatusResponseDO(
                resultSet.getString("id"),
                LeaseApplicationStatus.valueOf(resultSet.getString("status")),
                resultSet.getBoolean("isOpened"),
                resultSet.getObject("updatedAt", LocalDateTime.class),
                resultSet.getObject("createdAt", LocalDateTime.class),
                resultSet.getBoolean("isHighRisk")
        );
    }
}
