package com.leasing.calculator.repository;

import com.leasing.calculator.api.model.request.primitives.enums.LeaseApplicationStatus;
import com.leasing.calculator.domain.aggregates.request.StatusRequestDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class StatusRepositoryDAOImpl implements StatusRepositoryDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public StatusRepositoryDAOImpl(NamedParameterJdbcTemplate template) {
        this.namedParameterJdbcTemplate = template;
    }

    @Override
    public void createStatus(String id, boolean isHighRisk) {
        String query = """
                INSERT INTO STATUS (id, status, isOpened, updatedAt, createdAt, isHighRisk)
                VALUES (:id, :status, false, now(), now(), :isHighRisk)
                """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("status", LeaseApplicationStatus.NEW.toString())
                .addValue("isHighRisk", isHighRisk);
        namedParameterJdbcTemplate.update(query, params);

    }

    @Override
    public void updateStatusById(StatusRequestDO status) {
        String query = """
                UPDATE STATUS
                SET status = :status
                WHERE id = :id;
                """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", status.id())
                .addValue("status", status.applicationStatus().toString());
        namedParameterJdbcTemplate.update(query, params);
    }
}