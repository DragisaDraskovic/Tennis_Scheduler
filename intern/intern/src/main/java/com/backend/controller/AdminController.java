package com.backend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.backend.model.AdminEntity;
import com.backend.service.AdminServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@AllArgsConstructor
@ControllerAdvice
@RequestMapping(value = "/admin")
public class AdminController {

    private final AdminServiceImpl adminService;

    @GetMapping(value = "refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (!(authorizationHeader == null) && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String email = decodedJWT.getSubject();
                AdminEntity admin = adminService.getAdmin(email);
                String access_token = JWT.create()
                        .withSubject(admin.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 50 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", (List<?>) admin.getRole())
                        .sign(algorithm);
                response.setHeader("access_token", access_token);
                response.setHeader("refresh_token", refresh_token);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.sendError(FORBIDDEN.value());
            }
        }
        throw new RuntimeException("Refresh token is missing");

    }



}
