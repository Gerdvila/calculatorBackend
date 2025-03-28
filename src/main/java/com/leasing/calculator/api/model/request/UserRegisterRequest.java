package com.leasing.calculator.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "First name of the user", example = "John")
        @NotBlank(message = "First name is required")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
        String firstName,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Last name of the user", example = "Doe")
        @NotBlank(message = "Last name is required")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")
        String lastName,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "User login identifier", example = "Username123")
        @Size(min = 6, max = 20, message = "Login must contain between 6 and 20 characters")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Login must contain only letters and numbers")
        String login,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "User's password for the account", example = "SecurePass123!")
        @NotBlank(message = "Password is required")
        @Pattern(regexp = "^(?!.*\\s)(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
        String password,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Email address of the user", example = "johndoe@example.com")
        @NotBlank(message = "Email is required")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email must be valid")
        String email,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Phone number of the user, including country code", example = "+1234567890")
        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^\\+([0-9]{7,14})$", message = "Phone must contain only numbers")
        String phone
) {
}