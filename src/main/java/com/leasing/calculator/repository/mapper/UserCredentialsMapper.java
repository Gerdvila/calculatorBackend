package com.leasing.calculator.repository.mapper;

import com.leasing.calculator.domain.aggregates.response.UserCredentialResponseDO;
import com.leasing.calculator.domain.enums.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCredentialsMapper implements RowMapper<UserCredentialResponseDO> {
    @Override
    public UserCredentialResponseDO mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        return new UserCredentialResponseDO.Builder()
                .withId(resultSet.getString("id"))
                .withFirstName(resultSet.getString("first_name"))
                .withLastName(resultSet.getString("last_name"))
                .withLogin(resultSet.getString("username"))
                .withPassword(resultSet.getString("password"))
                .withEmail(resultSet.getString("email"))
                .withPhone(resultSet.getString("telephone"))
                .withRole(Role.valueOf(resultSet.getString("role"))).build();
    }
}