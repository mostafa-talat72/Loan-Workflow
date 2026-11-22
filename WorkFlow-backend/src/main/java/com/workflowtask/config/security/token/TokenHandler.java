package com.workflowtask.config.security.token;

import com.workflowtask.dto.UserAccountDTO;
import com.workflowtask.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Component
public class TokenHandler {

    private final SecretKey key;
    private final JwtParser jwtParser;
    private final Duration time;
    private final UserService userService;

    public TokenHandler(@Value("${token.secret}") String secret,
                        @Value("${token.time}") Duration time,
                        UserService userService) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parser().verifyWith(key).build();
        this.time = time;
        this.userService = userService;
    }
    public String createToken(UserAccountDTO userAccountDTO) {

        Date issueDate = new Date();

        Date expireDate = Date.from(issueDate.toInstant().plus(time));

        return Jwts.builder()
                .signWith(key)
                .subject(userAccountDTO.getUsername())
                .issuedAt(issueDate)
                .expiration(expireDate)
                .claim("role", userAccountDTO.getRole().getCode())
                .compact();
    }

    public UserAccountDTO validateToken(String token) {

        try {
            Claims claims = jwtParser.parseSignedClaims(token).getPayload();

            return userService.getUserByUsername(claims.getSubject());
        } catch (Exception e) {
            return null;
        }
    }

}
