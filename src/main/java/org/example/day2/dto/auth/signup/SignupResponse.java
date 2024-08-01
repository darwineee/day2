package org.example.day2.dto.auth.signup;

import lombok.Builder;

@Builder
public record SignupResponse(
        int id,
        String email,
        boolean inactive
) {
}
