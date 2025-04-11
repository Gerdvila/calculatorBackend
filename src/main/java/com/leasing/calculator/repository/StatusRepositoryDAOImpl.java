package com.leasing.calculator.repository;

import com.leasing.calculator.api.model.request.primitives.enums.LeaseApplicationStatus;
import com.leasing.calculator.domain.aggregates.request.StatusRequestDO;
import com.leasing.calculator.domain.aggregates.response.StatusResponseDO;
import com.leasing.calculator.repository.mapper.StatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<StatusResponseDO> getAllStatusByPage(long pageNumber) {
        String query = """
                SELECT *
                FROM STATUS
                LIMIT 7 OFFSET :offset
                """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("offset", (pageNumber - 1) * 7);

        return namedParameterJdbcTemplate.query(query, params, new StatusMapper());
    }

    @Override
    public void updateStatusRead(String id, boolean isOpened) {
        String query = """
                UPDATE STATUS
                SET isOpened = :isOpened
                WHERE id = :id;
                """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("isOpened", isOpened)
                .addValue("id", id);
        namedParameterJdbcTemplate.update(query, params);
    }

    @Override
    public Optional<StatusResponseDO> getStatusById(String id) {
        String query = """
                SELECT *
                FROM STATUS
                WHERE id = :id
                """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        return namedParameterJdbcTemplate.query(query, params, new StatusMapper())
                .stream()
                .findFirst();
    }
}