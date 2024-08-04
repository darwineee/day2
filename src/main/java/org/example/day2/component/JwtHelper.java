package org.example.day2.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtHelper {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expr}")
    private long expr;

    public @Nullable String extractEmail(@NotNull String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public boolean validateToken(
            @NotNull String token,
            @NotNull UserDetails user
    ) {
        String email = extractEmail(token);
        if (!user.getUsername().equals(email)) return false;
        return extractClaims(token, Claims::getExpiration).after(new Date());
    }

    public String generateToken(@NotNull String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expr))
                .signWith(getSecretKey())
                .compact();
    }

    private <T> T extractClaims(String token, Function<Claims, T> select) {
        final var claims = getClaims(token);
        return select.apply(claims);
    }

    private SecretKey getSecretKey() {
        var bytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
