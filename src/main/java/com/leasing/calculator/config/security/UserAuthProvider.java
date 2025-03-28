package com.leasing.calculator.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.leasing.calculator.api.model.exceptions.AppException;
import com.leasing.calculator.api.model.response.UserCredentialResponse;
import com.leasing.calculator.domain.aggregates.response.UserCredentialResponseDO;
import com.leasing.calculator.domain.enums.Role;
import com.leasing.calculator.repository.UserAuthRepositoryDAOImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
public class UserAuthProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private final UserAuthRepositoryDAOImpl userAuthRepositoryDAO;

    public UserAuthProvider(UserAuthRepositoryDAOImpl userAuthRepositoryDAO) {
        this.userAuthRepositoryDAO = userAuthRepositoryDAO;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserCredentialResponse user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000);

        return JWT.create()
                .withSubject(user.getLogin())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole().name())
                .withClaim("firstName", user.getFirstName())
                .withClaim("lastName", user.getLastName())
                .withClaim("username", user.getLogin())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();


        DecodedJWT decodedJWT = verifier.verify(token);

        UserCredentialResponse user = new UserCredentialResponse.Builder()
                .withLogin(decodedJWT.getSubject())
                .withId(decodedJWT.getClaim("id").asString())
                .withFirstName(decodedJWT.getClaim("firstName").asString())
                .withLastName(decodedJWT.getClaim("lastName").asString())
                .withLogin(decodedJWT.getClaim("username").asString())
                .withRole(Role.valueOf(decodedJWT.getClaim("role").asString()))
                .build();

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    public Authentication validateTokenStrongly(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decodedJWT = verifier.verify(token);
        UserCredentialResponseDO user = userAuthRepositoryDAO.getUserByLogin(decodedJWT.getClaim("username").asString())
                .orElseThrow(() -> new AppException("Unknown user ", HttpStatus.NOT_FOUND));

        return new UsernamePasswordAuthenticationToken(
                convertUserCredentialDAOResponseToUserCredentialResponse(user),
                null,
                Collections.emptyList());
    }

    private UserCredentialResponseDO convertUserCredentialDAOResponseToUserCredentialResponse(UserCredentialResponseDO userCredentialDAOResponse) {
        return new UserCredentialResponseDO.Builder()
                .withId(userCredentialDAOResponse.getId())
                .withFirstName(userCredentialDAOResponse.getFirstName())
                .withLastName(userCredentialDAOResponse.getLastName())
                .withLogin(userCredentialDAOResponse.getLogin())
                .withEmail(userCredentialDAOResponse.getEmail())
                .withPhone(userCredentialDAOResponse.getPhone())
                .withRole(userCredentialDAOResponse.getRole()).build();
    }
}