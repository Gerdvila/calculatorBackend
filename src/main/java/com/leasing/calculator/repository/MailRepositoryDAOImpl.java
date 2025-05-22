package com.leasing.calculator.repository;

import com.leasing.calculator.domain.aggregates.response.MailResponseDO;
import com.leasing.calculator.domain.aggregates.request.MailRequestDO;
import com.leasing.calculator.repository.mapper.MailMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MailRepositoryDAOImpl implements MailRepositoryDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MailRepositoryDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<MailResponseDO> selectMailByApplicationId(String applicationId) {
        String query = """
                    SELECT id, application_id, mail_text,created_at
                    FROM mail
                    WHERE application_id = :applicationId
                    ORDER BY created_at DESC
                """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("applicationId", applicationId);

        return namedParameterJdbcTemplate.query(query, params, new MailMapper());
    }

    @Override
    public void createMail(MailRequestDO mail) {
        String query = """
                    INSERT INTO mail (id, application_id, mail_text)
                    VALUES(:id, :applicationId, :mailText)
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", mail.id())
                .addValue("applicationId", mail.applicationId())
                .addValue("mailText", mail.mailText());

        namedParameterJdbcTemplate.update(query, params);
    }
}