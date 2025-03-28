package com.leasing.calculator.api.model.response;

import com.leasing.calculator.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User credentials response containing user details and authentication token")
public class UserCredentialResponse {

    @Schema(description = "Unique identifier of the user", example = "545d9fe7-d294-426c-af1c-e15ad71ed9e8")
    private String id;

    @Schema(description = "First name of the user", example = "John")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;

    @Schema(description = "Username or login identifier", example = "johndoe123")
    private String login;

    @Schema(description = "User password", example = "password123")
    private String password;

    @Schema(description = "User email address", example = "johndoe@example.com")
    private String email;

    @Schema(description = "User phone number", example = "+37060000000")
    private String phone;

    @Schema(description = "User role", example = "ADMIN")
    private Role role;

    @Schema(description = "JWT authentication token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    private UserCredentialResponse(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.login = builder.login;
        this.password = builder.password;
        this.email = builder.email;
        this.phone = builder.phone;
        this.role = builder.role;
    }

    public static class Builder {
        private String id;
        private String firstName;
        private String lastName;
        private String login;
        private String password;
        private String email;
        private String phone;
        private Role role;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder withRole(Role role) {
            this.role = role;
            return this;
        }

        public UserCredentialResponse build() {
            return new UserCredentialResponse(this);
        }
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Role getRole() {
        return role;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }
}