package com.leasing.calculator.repository;

import com.leasing.calculator.api.model.exceptions.AppException;
import com.leasing.calculator.api.model.request.aggregates.UserRegisterRequest;
import com.leasing.calculator.domain.aggregates.response.UserCredentialResponseDO;
import com.leasing.calculator.domain.enums.Role;
import com.leasing.calculator.repository.mapper.UserCredentialsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


class UserAuthRepositoryDAOImplTest {

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserAuthRepositoryDAOImpl userAuthRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private UserCredentialResponseDO createMockUser() {
        return new UserCredentialResponseDO.Builder()
                .withId("mock-id")
                .withFirstName("Mock")
                .withLastName("User")
                .withLogin("mockLogin")
                .withPassword("encodedPassword123")
                .withEmail("mock.user@example.com")
                .withPhone("+1234567890")
                .withRole(Role.ADMIN)
                .build();
    }

    @Test
    void testGetUserByLogin_UserExists() {
        String login = "mockLogin";

        UserCredentialResponseDO mockUser = createMockUser();

        when(namedParameterJdbcTemplate.query(any(String.class), any(SqlParameterSource.class), any(UserCredentialsMapper.class)))
                .thenReturn(java.util.List.of(mockUser));
        Optional<UserCredentialResponseDO> result = userAuthRepository.getUserByLogin(login);

        assertTrue(result.isPresent(), "Expected the user to be found");
        assertEquals(mockUser, result.get(), "The retrieved user should match the mocked user");
    }

    @Test
    void testGetUserByLogin_UserDoesNotExist() {
        String login = "nonexistentLogin";

        when(namedParameterJdbcTemplate.query(any(String.class), any(SqlParameterSource.class), any(UserCredentialsMapper.class)))
                .thenReturn(java.util.List.of());

        Optional<UserCredentialResponseDO> result = userAuthRepository.getUserByLogin(login);

        assertFalse(result.isPresent(), "Expected no user to be found");
    }

    @Test
    void testRegisterUser_Success() {
        UserRegisterRequest request = new UserRegisterRequest(
                "Mock", "User", "mockLogin", "Password@123", "mock.user@example.com", "+1234567890"
        );

        String encodedPassword = "encodedPassword123";
        UserCredentialResponseDO mockUser = createMockUser();

        when(passwordEncoder.encode(request.password())).thenReturn(encodedPassword);
        when(namedParameterJdbcTemplate.query(any(String.class), any(SqlParameterSource.class), any(RowMapper.class)))
                .thenReturn(List.of(mockUser));

        UserCredentialResponseDO result = userAuthRepository.registerUser(request);

        assertNotNull(result, "Expected the registered user to be returned");
        assertEquals(mockUser, result, "The registered user should match the mocked user");

        verify(passwordEncoder).encode(request.password());
        verify(namedParameterJdbcTemplate).update(any(String.class), any(SqlParameterSource.class));
        verify(namedParameterJdbcTemplate).query(any(String.class), any(SqlParameterSource.class), any(RowMapper.class));
    }

    @Test
    void testRegisterUser_DataAccessException_ThrowAppException() {
        UserRegisterRequest request = new UserRegisterRequest(
                "Mock", "User", "mockLogin", "Password@123", "mock.user@example.com", "+1234567890"
        );

        String encodedPassword = "encodedPassword123";

        when(passwordEncoder.encode(request.password())).thenReturn(encodedPassword);
        doThrow(new DataAccessException("Database error") {}).when(namedParameterJdbcTemplate)
                .update(any(String.class), any(SqlParameterSource.class)); // Simulate DB failure


        AppException exception = assertThrows(AppException.class, () -> userAuthRepository.registerUser(request));

        assertEquals("Could not register User.", exception.getMessage(), "Expected exception message to match");
        verify(passwordEncoder).encode(request.password());
        verify(namedParameterJdbcTemplate).update(any(String.class), any(SqlParameterSource.class));
    }
}