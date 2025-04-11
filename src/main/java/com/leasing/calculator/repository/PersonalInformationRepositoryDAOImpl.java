package com.leasing.calculator.repository;

import com.leasing.calculator.domain.aggregates.request.PersonalInformationRequestDO;
import com.leasing.calculator.domain.aggregates.response.PersonalInformationResponseDO;
import com.leasing.calculator.repository.mapper.PersonalInformationMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PersonalInformationRepositoryDAOImpl implements PersonalInformationRepositoryDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PersonalInformationRepositoryDAOImpl(NamedParameterJdbcTemplate template) {
        this.namedParameterJdbcTemplate = template;
    }

    public String createPersonalInformation(PersonalInformationRequestDO personalInformationDAORequest) {
        String id = UUID.randomUUID().toString();
        String query = """
                INSERT INTO PERSONAL_INFORMATION (id,firstName, lastName, email, phoneNumber, pid, dateOfBirth, maritalStatus, numberOfChildren, citizenship, monthlyIncome, languagePref)
                VALUES (:id,:firstName, :lastName, :email, :phoneNumber, :pid, :dateOfBirth, :maritalStatus, :numberOfChildren, :citizenship, :monthlyIncome, :languagePref)
                RETURNING id
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("firstName", personalInformationDAORequest.getFirstName())
                .addValue("lastName", personalInformationDAORequest.getLastName())
                .addValue("email", personalInformationDAORequest.getEmail())
                .addValue("phoneNumber", personalInformationDAORequest.getPhoneNumber())
                .addValue("pid", personalInformationDAORequest.getPid())
                .addValue("dateOfBirth", personalInformationDAORequest.getDateOfBirth())
                .addValue("maritalStatus", personalInformationDAORequest.getMaritalStatus())
                .addValue("numberOfChildren", personalInformationDAORequest.getNumberOfChildren())
                .addValue("citizenship", personalInformationDAORequest.getCitizenship())
                .addValue("monthlyIncome", personalInformationDAORequest.getMonthlyIncome())
                .addValue("languagePref", personalInformationDAORequest.getLanguagePref());

        return namedParameterJdbcTemplate.queryForObject(query, params, String.class);
    }

    @Override
    public List<PersonalInformationResponseDO> getAllPersonalInformationByPage(long pageNumber){
        String query = """
            SELECT *
            FROM PERSONAL_INFORMATION
            LIMIT 7 OFFSET :offset
            """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("offset", (pageNumber - 1) * 7);

        return namedParameterJdbcTemplate.query(query, params, new PersonalInformationMapper());

    }

    public Optional<PersonalInformationResponseDO> getPersonalInformationById(String pid) {
        String query = """
                SELECT *
                FROM PERSONAL_INFORMATION
                WHERE id = :id
                """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", pid);

        return namedParameterJdbcTemplate.query(query, params, new PersonalInformationMapper())
                .stream()
                .findFirst();

    }
}
