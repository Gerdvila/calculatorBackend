package com.leasing.calculator.service;

import com.leasing.calculator.api.model.exceptions.AppException;
import com.leasing.calculator.api.model.request.UserLoginRequest;
import com.leasing.calculator.api.model.request.UserRegisterRequest;
import com.leasing.calculator.api.model.response.UserCredentialResponse;
import com.leasing.calculator.domain.aggregates.response.UserCredentialResponseDO;
import com.leasing.calculator.domain.enums.Role;
import com.leasing.calculator.repository.UserAuthRepositoryDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class UserAuthServiceTest {

    @Mock
    private UserAuthRepositoryDAO userAuthRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserAuthService userAuthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        UserLoginRequest request = new UserLoginRequest("mockLogin", "Password@123");
        UserCredentialResponseDO mockUser = createMockUser();

        when(userAuthRepository.getUserByLogin(request.login())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(request.password(), mockUser.getPassword())).thenReturn(true);

        UserCredentialResponse result = userAuthService.login(request);

        assertNotNull(result);
        assertEquals(mockUser.getLogin(), result.getLogin());
        verify(userAuthRepository).getUserByLogin(request.login());
        verify(passwordEncoder).matches(request.password(), mockUser.getPassword());
    }

    @Test
    void testLogin_UnknownUser() {
        UserLoginRequest request = new UserLoginRequest("unknownUser", "Password@123");
        when(userAuthRepository.getUserByLogin(request.login())).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> userAuthService.login(request));
        assertEquals("Unknown user ", exception.getMessage());
        assertEquals(HttpStatus.IM_USED, exception.getHttpStatus());
    }

    @Test
    void testLogin_IncorrectPassword() {
        UserLoginRequest request = new UserLoginRequest("mockLogin", "WrongPassword");
        UserCredentialResponseDO mockUser = createMockUser();

        when(userAuthRepository.getUserByLogin(request.login())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(request.password(), mockUser.getPassword())).thenReturn(false);

        AppException exception = assertThrows(AppException.class, () -> userAuthService.login(request));
        assertEquals("Unknown user ", exception.getMessage());
    }

    @Test
    void testRegister_Success() {
        UserRegisterRequest request = new UserRegisterRequest(
                "Mock", "User", "mockLogin", "Password@123", "mock.user@example.com", "+1234567890"
        );

        UserCredentialResponseDO mockUser = createMockUser();
        when(userAuthRepository.registerUser(request)).thenReturn(mockUser);

        UserCredentialResponse result = userAuthService.register(request);

        assertNotNull(result);
        assertEquals(mockUser.getLogin(), result.getLogin());
        verify(userAuthRepository).registerUser(request);
    }

    @Test
    void testRegister_RepositoryException() {
        UserRegisterRequest request = new UserRegisterRequest(
                "Mock", "User", "mockLogin", "Password@123", "mock.user@example.com", "+1234567890"
        );

        when(userAuthRepository.registerUser(request)).thenThrow(new AppException("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR));

        AppException exception = assertThrows(AppException.class, () -> userAuthService.register(request));
        assertEquals("Registration failed", exception.getMessage());
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
}