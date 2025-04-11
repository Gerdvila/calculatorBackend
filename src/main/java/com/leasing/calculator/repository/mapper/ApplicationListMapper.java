package com.leasing.calculator.repository.mapper;


import com.leasing.calculator.api.model.request.primitives.enums.LeaseApplicationStatus;
import com.leasing.calculator.domain.aggregates.response.ApplicationListResponseDO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ApplicationListMapper implements RowMapper<ApplicationListResponseDO> {
    @Override
    public ApplicationListResponseDO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new ApplicationListResponseDO(
                resultSet.getString("id"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getBoolean("isOpened"),
                LeaseApplicationStatus.valueOf(resultSet.getString("status")),
                resultSet.getObject("createdAt", LocalDateTime.class),
                resultSet.getBoolean("isHighRisk")
        );
    }
}