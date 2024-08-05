package org.example.day2.core.dto.auth.signup;

import lombok.Builder;
import org.example.day2.core.model.user.User;

@Builder
public record SignupResponse(
        int id,
        String email,
        boolean active
) {
    public static SignupResponse from(User user) {
        return SignupResponse.builder()
                .id(user.id())
                .email(user.email())
                .active(user.active())
                .build();
    }
}
