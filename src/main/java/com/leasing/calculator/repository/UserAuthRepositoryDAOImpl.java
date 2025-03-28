package com.leasing.calculator.repository;

import com.leasing.calculator.api.model.exceptions.AppException;
import com.leasing.calculator.api.model.request.aggregates.UserRegisterRequest;
import com.leasing.calculator.domain.aggregates.response.UserCredentialResponseDO;
import com.leasing.calculator.domain.enums.Role;
import com.leasing.calculator.repository.mapper.UserCredentialsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserAuthRepositoryDAOImpl implements UserAuthRepositoryDAO {

    PasswordEncoder passwordEncoder;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserAuthRepositoryDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, PasswordEncoder passwordEncoder) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserCredentialResponseDO> getUserByLogin(String login) {
        String query = """
                SELECT id, first_name, last_name, username, password, email, telephone, role
                FROM "User"
                WHERE username = :login
                """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("login", login);
        return namedParameterJdbcTemplate.query(query, params, new UserCredentialsMapper())
                .stream()
                .findFirst();
    }

    @Override
    public UserCredentialResponseDO registerUser(UserRegisterRequest userRegisterRequest) {
        try{
            String generatedUUID = UUID.randomUUID().toString();

            String query = """
                INSERT INTO "User"(id, first_name, last_name, username, password, email, telephone, role)
                VALUES(:id, :firstName, :lastName, :login, :password, :email, :telephone, :role)
                """;

            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", generatedUUID)
                    .addValue("firstName", userRegisterRequest.firstName())
                    .addValue("lastName", userRegisterRequest.lastName())
                    .addValue("login", userRegisterRequest.login())
                    .addValue("password", passwordEncoder.encode(userRegisterRequest.password()))
                    .addValue("email", userRegisterRequest.email())
                    .addValue("telephone", userRegisterRequest.phone())
                    .addValue("role", Role.ADMIN.toString());

            namedParameterJdbcTemplate.update(query, params);
            return getUserByLogin(userRegisterRequest.login()).orElseThrow();
        }catch (DataAccessException e){
            throw new AppException("Could not register User.", HttpStatus.INTERNAL_SERVER_ERROR, "Problems with database insertion.");
        }
    }
}