package org.example.day2.dto.auth.signup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.example.day2.utils.Message;
import org.example.day2.utils.RegexP;

public record SignupRequest(
        @NotBlank(message = Message.VALIDATION_EMAIL_BLANK)
        String email,
        @Pattern(regexp = RegexP.VALIDATE_PWD, message = Message.VALIDATION_PWD_REQ)
        String password
) {
}
