package org.example.day2.core.service.auth;

import org.example.day2.core.dto.auth.login.LoginRequest;
import org.example.day2.core.dto.auth.login.LoginResponse;
import org.example.day2.core.dto.auth.signup.SignupRequest;
import org.example.day2.core.dto.auth.signup.SignupResponse;
import org.example.day2.core.exception.UnknownException;
import org.example.day2.core.exception.auth.UserExistedException;

public interface AuthService {
    SignupResponse signUp(SignupRequest request) throws UserExistedException, UnknownException;
    LoginResponse login(LoginRequest request) throws UnknownException;
}
