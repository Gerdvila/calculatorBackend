package com.leasing.calculator.repository.mapper;

import com.leasing.calculator.domain.aggregates.response.NoteResponseDO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteMapper implements RowMapper<NoteResponseDO> {

    @Override
    public NoteResponseDO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new NoteResponseDO(
                resultSet.getString("id"),
                resultSet.getString("application_id"),
                resultSet.getString("note_text"),
                resultSet.getTimestamp("created_at").toLocalDateTime()
        );
    }
}