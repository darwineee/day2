package org.example.day2.core.dto.auth.login;

import jakarta.validation.constraints.NotBlank;
import org.example.day2.core.utils.Message;

public record LoginRequest(
        @NotBlank(message = Message.VALIDATION_EMAIL_BLANK)
        String email,
        @NotBlank(message = Message.VALIDATION_PWD_BLANK)
        String password
) {
}
