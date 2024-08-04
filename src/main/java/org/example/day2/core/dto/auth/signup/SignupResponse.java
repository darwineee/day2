package org.example.day2.core.dto.auth.signup;

import lombok.Builder;

@Builder
public record SignupResponse(
        int id,
        String email,
        boolean active
) {
}
