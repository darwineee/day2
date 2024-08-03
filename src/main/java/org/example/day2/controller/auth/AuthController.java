package org.example.day2.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.day2.component.JWTHelper;
import org.example.day2.dto.auth.signup.SignupRequest;
import org.example.day2.dto.auth.signup.SignupResponse;
import org.example.day2.dto.wrapper.ErrorRsp;
import org.example.day2.exception.UnknownException;
import org.example.day2.exception.auth.UserExistedException;
import org.example.day2.service.auth.AuthService;
import org.example.day2.utils.ErrCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authManager;
    private final JWTHelper jwtHelper;

    @PostMapping("/auth/signup")
    public ResponseEntity<SignupResponse> signup(
            @Valid
            @RequestBody
            SignupRequest request
    ) throws UserExistedException, UnknownException {
        var rsp = authService.signUp(request);
        return ResponseEntity.ok(rsp);
    }

    @ExceptionHandler(UserExistedException.class)
    public ResponseEntity<ErrorRsp<String>> handleException(Exception e) {
        var body = new ErrorRsp<>(ErrCode.USER_EXISTED, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}
