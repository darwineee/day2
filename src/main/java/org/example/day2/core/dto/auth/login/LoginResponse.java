package org.example.day2.core.dto.auth.login;

import lombok.Builder;

@Builder
public record LoginResponse(
        int id,
        String email,
        String token
) {
}
