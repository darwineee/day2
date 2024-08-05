package org.example.day2.core.model.user;

import lombok.Builder;
import org.example.day2.core.dto.auth.signup.SignupRequest;

@Builder
public record User(
        int id,
        String email,
        String password,
        boolean active
) {
    public static User from(SignupRequest request, String encodedPassword) {
        return User.builder()
                .email(request.email())
                .password(encodedPassword)
                .active(true)
                .build();
    }
}

