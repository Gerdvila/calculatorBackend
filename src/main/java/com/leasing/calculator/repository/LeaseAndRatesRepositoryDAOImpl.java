package com.leasing.calculator.repository;


import com.leasing.calculator.domain.aggregates.request.CreateLeaseAndRatesRequestDO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

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
}