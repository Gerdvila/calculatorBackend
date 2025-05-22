package com.leasing.calculator.repository;

import com.leasing.calculator.domain.aggregates.response.AcceptedApplicationResponseDO;
import com.leasing.calculator.domain.aggregates.response.ApplicationDailyCountResponseDO;
import com.leasing.calculator.domain.aggregates.response.ApplicationMonthlyCountResponseDO;
import com.leasing.calculator.domain.aggregates.response.HighRiskResponseDO;
import com.leasing.calculator.domain.aggregates.response.StatusCountResponseDO;
import com.leasing.calculator.repository.mapper.AcceptedApplicationMapper;
import com.leasing.calculator.repository.mapper.ApplicationDailyMapper;
import com.leasing.calculator.repository.mapper.ApplicationMonthlyCountMapper;
import com.leasing.calculator.repository.mapper.HighRiskMapper;
import com.leasing.calculator.repository.mapper.StatusCountMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatisticRepositoryDAOImpl implements StatisticRepositoryDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public StatisticRepositoryDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public StatusCountResponseDO countStatus() {
        String query = """
                        SELECT
                        COUNT(CASE WHEN STATUS.status = 'NEW' AND EXTRACT(YEAR FROM STATUS.createdAt) = EXTRACT(YEAR FROM CURRENT_DATE) THEN 1 END) AS newCount,
                        COUNT(CASE WHEN STATUS.status = 'ACCEPTED' AND EXTRACT(YEAR FROM STATUS.createdAt) = EXTRACT(YEAR FROM CURRENT_DATE) THEN 1 END) AS acceptedCount,
                        COUNT(CASE WHEN STATUS.status = 'REJECTED' AND EXTRACT(YEAR FROM STATUS.createdAt) = EXTRACT(YEAR FROM CURRENT_DATE) THEN 1 END) AS rejectedCount,
                        COUNT(CASE WHEN STATUS.status = 'PENDING' AND EXTRACT(YEAR FROM STATUS.createdAt) = EXTRACT(YEAR FROM CURRENT_DATE) THEN 1 END) AS pendingCount
                        FROM STATUS;
                """;
        return namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource(), new StatusCountMapper());
    }

    @Override
    public List<ApplicationDailyCountResponseDO> countApplicationsCurrentMonthDaily() {
        String query = """
                    SELECT
                        COALESCE(COUNT(STATUS.id), 0) AS applicationCount,
                        date_series.day
                    FROM (
                        SELECT generate_series(date_trunc('month', CURRENT_DATE),
                                               date_trunc('month', CURRENT_DATE) + interval '1 month' - interval '1 day',
                                               interval '1 day') AS day
                    ) AS date_series
                    LEFT JOIN STATUS
                        ON DATE(STATUS.createdAt) = date_series.day
                        AND EXTRACT(MONTH FROM STATUS.createdAt) = EXTRACT(MONTH FROM CURRENT_DATE)
                        AND EXTRACT(YEAR FROM STATUS.createdAt) = EXTRACT(YEAR FROM CURRENT_DATE)
                    GROUP BY date_series.day
                    ORDER BY date_series.day;
                """;
        return namedParameterJdbcTemplate.query(query, new ApplicationDailyMapper());
    }

    @Override
    public HighRiskResponseDO countHighRiskApplications() {
        String query = """
                      SELECT
                          COUNT(CASE WHEN isHighRisk = TRUE
                                     AND date_trunc('month', createdAt) = date_trunc('month', CURRENT_DATE)
                                     THEN 1 ELSE NULL END) AS currentMonthHighRiskCount,
                      
                          COUNT(CASE WHEN isHighRisk = TRUE
                                     AND date_trunc('month', createdAt) = date_trunc('month', CURRENT_DATE) - interval '1 month'
                                     THEN 1 ELSE NULL END) AS previousMonthHighRiskCount
                      FROM STATUS
                      WHERE createdAt >= date_trunc('year', CURRENT_DATE) - interval '1 month'
                            AND createdAt < date_trunc('year', CURRENT_DATE) + interval '1 year';
                """;
        return namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource(), new HighRiskMapper());
    }

    @Override
    public AcceptedApplicationResponseDO countAcceptedApplicationsTotalSum() {
        String query = """
                SELECT
                     SUM(CASE
                             WHEN EXTRACT(YEAR FROM S.createdAt) = EXTRACT(YEAR FROM CURRENT_DATE)
                             THEN L.monthlyPayment * L.period
                             ELSE 0
                         END) AS thisYearTotalPayments,
                     SUM(CASE
                             WHEN EXTRACT(YEAR FROM S.createdAt) = EXTRACT(YEAR FROM CURRENT_DATE) - 1
                             THEN L.monthlyPayment * L.period
                             ELSE 0
                         END) AS lastYearTotalPayments
                 FROM LEASE L
                 JOIN STATUS S ON L.id = S.id
                 WHERE S.status = 'ACCEPTED';
                """;
        return namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource(), new AcceptedApplicationMapper());
    }

    @Override
    public ApplicationMonthlyCountResponseDO getApplicationMonthlyCount() {
        String query = """
                SELECT
                    COUNT(CASE WHEN EXTRACT(MONTH FROM STATUS.createdAt) = EXTRACT(MONTH FROM CURRENT_DATE) THEN 1 END) AS thisMonthCount,
                    COUNT(CASE WHEN EXTRACT(MONTH FROM STATUS.createdAt) = EXTRACT(MONTH FROM CURRENT_DATE) - 1 THEN 1 END) AS previousMonthCount
                FROM STATUS;
                """;
        return namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource(), new ApplicationMonthlyCountMapper());
    }
}