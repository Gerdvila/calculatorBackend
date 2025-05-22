package com.leasing.calculator.repository.mapper;

import com.leasing.calculator.domain.aggregates.response.MailResponseDO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MailMapper implements RowMapper<MailResponseDO> {

    @Override
    public MailResponseDO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new MailResponseDO(
                resultSet.getString("id"),
                resultSet.getString("application_id"),
                resultSet.getString("mail_text"),
                resultSet.getTimestamp("created_at").toLocalDateTime()
        );
    }
}