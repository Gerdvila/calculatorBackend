package com.leasing.calculator.repository.mapper;

import com.leasing.calculator.domain.aggregates.response.LeaseAndRatesResponseDO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaseAndRatesMapper implements RowMapper<LeaseAndRatesResponseDO> {
    @Override
    public LeaseAndRatesResponseDO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new LeaseAndRatesResponseDO(
                resultSet.getString("id"),
                resultSet.getString("make"),
                resultSet.getString("model"),
                resultSet.getString("modelVariant"),
                resultSet.getString("year"),
                resultSet.getString("fuelType"),
                resultSet.getDouble("enginePower"),
                resultSet.getDouble("engineSize"),
                resultSet.getString("url"),
                resultSet.getString("offer"),
                resultSet.getBoolean("terms"),
                resultSet.getBoolean("confirmation"),
                resultSet.getBigDecimal("carValue"),
                resultSet.getInt("period"),
                resultSet.getBigDecimal("downPayment"),
                resultSet.getInt("residualValuePercentage"),
                resultSet.getBoolean("isEcoFriendly"),
                resultSet.getBigDecimal("monthlyPayment")
        );
    }
}