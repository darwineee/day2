package org.example.day2.core.dto.auth.login;

import lombok.Builder;
import org.example.day2.core.model.user.User;

@Builder
public record LoginResponse(
        int id,
        String email,
        String token
) {
    public static LoginResponse from(User user, String token) {
        return LoginResponse.builder()
                .id(user.id())
                .email(user.email())
                .token(token)
                .build();
    }
}
