package com.leasing.calculator.repository;


import com.leasing.calculator.domain.aggregates.request.CreateLeaseAndRatesRequestDO;
import com.leasing.calculator.domain.aggregates.response.LeaseAndRatesResponseDO;
import com.leasing.calculator.repository.mapper.LeaseAndRatesMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LeaseAndRatesRepositoryDAOImpl implements LeaseAndRatesRepositoryDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public LeaseAndRatesRepositoryDAOImpl(NamedParameterJdbcTemplate template) {
        this.namedParameterJdbcTemplate = template;
    }

    @Override
    public void createLeaseAndRate(CreateLeaseAndRatesRequestDO createLeaseAndRatesRequestDO, String pid) {
        String query = """
                INSERT INTO lease (id, make, model, modelVariant, year, fuelType, enginePower, engineSize, url, offer, terms, confirmation, carValue, period, downPayment, residualValuePercentage, isEcoFriendly, monthlyPayment)
                VALUES (:id, :make, :model, :modelVariant, :year, :fuelType, :enginePower, :engineSize, :url, :offer, :terms, :confirmation, :carValue, :period, :downPayment, :residualValuePercentage, :isEcoFriendly, :monthlyPayment)
                """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", pid)
                .addValue("make", createLeaseAndRatesRequestDO.getMake())
                .addValue("model", createLeaseAndRatesRequestDO.getModel())
                .addValue("modelVariant", createLeaseAndRatesRequestDO.getModelVariant())
                .addValue("year", createLeaseAndRatesRequestDO.getYear())
                .addValue("fuelType", createLeaseAndRatesRequestDO.getFuelType())
                .addValue("enginePower", createLeaseAndRatesRequestDO.getEnginePower())
                .addValue("engineSize", createLeaseAndRatesRequestDO.getEngineSize())
                .addValue("url", createLeaseAndRatesRequestDO.getUrl())
                .addValue("offer", createLeaseAndRatesRequestDO.getOffer())
                .addValue("terms", createLeaseAndRatesRequestDO.getTerms())
                .addValue("confirmation", createLeaseAndRatesRequestDO.getConfirmation())
                .addValue("carValue", createLeaseAndRatesRequestDO.getCarValue())
                .addValue("period", createLeaseAndRatesRequestDO.getPeriod())
                .addValue("downPayment", createLeaseAndRatesRequestDO.getDownPayment())
                .addValue("residualValuePercentage", createLeaseAndRatesRequestDO.getResidualValuePercentage())
                .addValue("isEcoFriendly", createLeaseAndRatesRequestDO.getIsEcoFriendly())
                .addValue("monthlyPayment", createLeaseAndRatesRequestDO.getMonthlyPayment());

        namedParameterJdbcTemplate.update(query, params);
    }

    @Override
    public Optional<LeaseAndRatesResponseDO> getLeaseAndRateById(String pid) {
        String query = """
                SELECT *
                FROM lease
                WHERE id = :id
                """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", pid);

        return namedParameterJdbcTemplate.query(query,params, new LeaseAndRatesMapper())
                .stream()
                .findFirst();
    }

    @Override
    public List<LeaseAndRatesResponseDO> getAllLeaseAndRatesByPage(long pageNumber){
        String query = """
            SELECT *
            FROM LEASE
            LIMIT 7 OFFSET :offset
            """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("offset", (pageNumber - 1) * 7);

        return namedParameterJdbcTemplate.query(query, params, new LeaseAndRatesMapper());
    }
}