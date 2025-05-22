package com.leasing.calculator.repository;

import com.leasing.calculator.domain.aggregates.request.NoteRequestDO;
import com.leasing.calculator.domain.aggregates.response.NoteResponseDO;
import com.leasing.calculator.repository.mapper.NoteMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoteRepositoryDAOImpl implements NoteRepositoryDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public NoteRepositoryDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<NoteResponseDO> selectNotesById(String applicationId) {
        String query = """
                    SELECT id, application_id, note_text, created_at
                    FROM notes
                    WHERE application_id = :applicationId
                    ORDER BY created_at DESC;
                """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("applicationId", applicationId);

        return namedParameterJdbcTemplate.query(query, params, new NoteMapper());

    }

    @Override
    public String createNote(NoteRequestDO note) {
        String query = """
                    INSERT INTO notes (id, application_id, note_text)
                    VALUES(:id, :applicationId, :noteText)
                    RETURNING id
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", note.id())
                .addValue("applicationId", note.applicationId())
                .addValue("noteText", note.noteText());

        return namedParameterJdbcTemplate.queryForObject(query, params, String.class);
    }
}