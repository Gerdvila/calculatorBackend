package com.leasing.calculator.api.model.request.aggregates;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserLoginRequest(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Login field of user login request", example = "Username123!")
        @Size(min = 6, max = 20, message = "Login must contain between 6 and 20 characters")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Login must contain only letters and numbers")
        String login,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Password for user account", example = "Password1337%")
        @NotBlank(message = "Password is required")
        @Pattern(regexp = "^(?!.*\\s)(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
        String password
) {
}