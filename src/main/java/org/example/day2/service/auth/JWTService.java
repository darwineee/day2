package org.example.day2.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String secret;

    public @Nullable String extractEmail(@NonNull String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public boolean validateToken(
            @NonNull String token,
            @NonNull UserDetails user
    ) {
        String email = extractEmail(token);
        if (!user.getUsername().equals(email)) return false;
        return extractClaims(token, Claims::getExpiration).after(new Date());
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

    public String createJWT(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSecretKey())
                .compact();
    }
}
