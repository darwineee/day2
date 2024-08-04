package org.example.day2.controller.v1.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.day2.core.dto.auth.login.LoginRequest;
import org.example.day2.core.dto.auth.login.LoginResponse;
import org.example.day2.core.dto.auth.signup.SignupRequest;
import org.example.day2.core.dto.auth.signup.SignupResponse;
import org.example.day2.core.dto.wrapper.ErrorRsp;
import org.example.day2.core.exception.UnknownException;
import org.example.day2.core.exception.auth.UserExistedException;
import org.example.day2.service.auth.AuthServiceImpl;
import org.example.day2.core.utils.ErrCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<SignupResponse> signup(
            @Valid
            @RequestBody
            SignupRequest request
    ) throws UserExistedException, UnknownException {
        var rsp = authService.signUp(request);
        return ResponseEntity.ok(rsp);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(
            @Valid
            @RequestBody
            LoginRequest request
    ) throws AuthenticationException, UnknownException {
        var rsp = authService.login(request);
        return ResponseEntity.ok(rsp);
    }

    @ExceptionHandler(UserExistedException.class)
    public ResponseEntity<ErrorRsp<String>> handle(UserExistedException e) {
        var body = new ErrorRsp<>(ErrCode.USER_EXISTED, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorRsp<String>> handle(AuthenticationException e) {
        int errCode;
        if (e instanceof DisabledException || e instanceof LockedException) {
            errCode = ErrCode.ACC_INACTIVE;
        } else {
            errCode = ErrCode.UNAUTHENTICATED;
        }
        var body = new ErrorRsp<>(errCode, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
