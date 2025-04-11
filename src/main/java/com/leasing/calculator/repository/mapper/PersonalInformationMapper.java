package com.leasing.calculator.repository.mapper;

import com.leasing.calculator.domain.aggregates.response.PersonalInformationResponseDO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonalInformationMapper implements RowMapper<PersonalInformationResponseDO> {
    @Override
    public PersonalInformationResponseDO mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        return new PersonalInformationResponseDO(
                resultSet.getString("id"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("email"),
                resultSet.getString("phoneNumber"),
                resultSet.getString("pid"),
                resultSet.getTimestamp("dateOfBirth").toLocalDateTime(),
                resultSet.getString("maritalStatus"),
                resultSet.getInt("numberOfChildren"),
                resultSet.getString("citizenship"),
                resultSet.getBigDecimal("monthlyIncome"),
                resultSet.getString("languagePref")
        );
    }
}