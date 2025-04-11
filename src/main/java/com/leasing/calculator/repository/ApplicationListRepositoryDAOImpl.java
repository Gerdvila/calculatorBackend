package com.leasing.calculator.repository;

import com.leasing.calculator.domain.aggregates.request.FetchLeaseBySearchQueryRequestDO;
import com.leasing.calculator.domain.aggregates.response.ApplicationListResponseDO;
import com.leasing.calculator.repository.mapper.ApplicationListMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApplicationListRepositoryDAOImpl implements ApplicationListRepositoryDAO {

    final int PAGE_SIZE = 30;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ApplicationListRepositoryDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<ApplicationListResponseDO> sortAndFilterByStatus(FetchLeaseBySearchQueryRequestDO applicationListRequest) {

        String query = """
        SELECT PERSONAL_INFORMATION.id, PERSONAL_INFORMATION.firstName, PERSONAL_INFORMATION.lastName,
               STATUS.isOpened, STATUS.status, STATUS.createdAt, STATUS.isHighRisk
        FROM PERSONAL_INFORMATION
        JOIN STATUS ON PERSONAL_INFORMATION.id = STATUS.id
        WHERE STATUS.status IN (:statuses)
        ORDER BY STATUS.createdAt DESC
        LIMIT :limit OFFSET :offset
        """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("statuses", applicationListRequest.statuses())
                .addValue("limit", PAGE_SIZE)
                .addValue("offset", (applicationListRequest.page() - 1) * PAGE_SIZE);

        return namedParameterJdbcTemplate.query(query, params, new ApplicationListMapper());
    }

    @Override
    public List<ApplicationListResponseDO> sortApplicationsByTimestamp(FetchLeaseBySearchQueryRequestDO applicationListRequest) {
        String query = """
        SELECT PERSONAL_INFORMATION.id, PERSONAL_INFORMATION.firstName, PERSONAL_INFORMATION.lastName,
               STATUS.isOpened, STATUS.status, STATUS.createdAt, STATUS.isHighRisk
        FROM PERSONAL_INFORMATION
        JOIN STATUS ON PERSONAL_INFORMATION.id = STATUS.id
        ORDER BY STATUS.createdAt DESC
        LIMIT :limit OFFSET :offset
        """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", PAGE_SIZE)
                .addValue("offset", (applicationListRequest.page() - 1) * PAGE_SIZE);

        return namedParameterJdbcTemplate.query(query, params, new ApplicationListMapper());
    }

    @Override
    public List<ApplicationListResponseDO> sortAndFilterBySearchQuery(FetchLeaseBySearchQueryRequestDO  applicationListRequest) {
        String finalName= "%" + applicationListRequest.searchQuery().toLowerCase().trim() + "%";

        String query = """
        SELECT PERSONAL_INFORMATION.id, PERSONAL_INFORMATION.firstName, PERSONAL_INFORMATION.lastName,
               STATUS.isOpened, STATUS.status, STATUS.createdAt, STATUS.isHighRisk
        FROM PERSONAL_INFORMATION
        JOIN STATUS ON PERSONAL_INFORMATION.id = STATUS.id
        WHERE (CONCAT(LOWER(PERSONAL_INFORMATION.firstName), ' ', LOWER(PERSONAL_INFORMATION.lastName)) LIKE :searchQuery
                    OR CAST(PERSONAL_INFORMATION.id AS TEXT) LIKE :searchQuery)
        ORDER BY STATUS.createdAt DESC
        LIMIT :limit OFFSET :offset
        """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("searchQuery", finalName)
                .addValue("limit", PAGE_SIZE)
                .addValue("offset", (applicationListRequest.page() - 1) * PAGE_SIZE);

        return namedParameterJdbcTemplate.query(query, params, new ApplicationListMapper());
    }

    @Override
    public List<ApplicationListResponseDO> sortAndFilterByStatusAndSearchQuery(FetchLeaseBySearchQueryRequestDO  applicationListRequest) {
        String finalName= "%" + applicationListRequest.searchQuery().toLowerCase().trim() + "%";

        String query = """
                SELECT PERSONAL_INFORMATION.id, PERSONAL_INFORMATION.firstName, PERSONAL_INFORMATION.lastName,
                    STATUS.isOpened, STATUS.status, STATUS.createdAt, STATUS.isHighRisk
                FROM PERSONAL_INFORMATION
                JOIN STATUS ON PERSONAL_INFORMATION.id = STATUS.id
                WHERE STATUS.status IN (:statuses)
                AND (CONCAT(LOWER(PERSONAL_INFORMATION.firstName), ' ', LOWER(PERSONAL_INFORMATION.lastName)) LIKE :searchQuery
                    OR CAST(PERSONAL_INFORMATION.id AS TEXT) LIKE :searchQuery)
                ORDER BY STATUS.createdAt DESC
                LIMIT :limit OFFSET :offset
                  """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("statuses",applicationListRequest.statuses())
                .addValue("searchQuery", finalName)
                .addValue("limit", PAGE_SIZE)
                .addValue("offset", (applicationListRequest.page() - 1) * PAGE_SIZE);

        return namedParameterJdbcTemplate.query(query, params, new ApplicationListMapper());
    }
}