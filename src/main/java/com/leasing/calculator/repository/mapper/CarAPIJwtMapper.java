package com.leasing.calculator.repository.mapper;

import com.leasing.calculator.util.CarAPIJwt;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarAPIJwtMapper implements RowMapper<CarAPIJwt> {
    @Override
    public CarAPIJwt mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CarAPIJwt(rs.getString("jwt"), rs.getInt("expires_at"));
    }
}