package org.example.day2.service.auth;

import lombok.RequiredArgsConstructor;
import org.example.day2.component.JwtHelper;
import org.example.day2.core.dto.auth.login.LoginRequest;
import org.example.day2.core.dto.auth.login.LoginResponse;
import org.example.day2.core.dto.auth.signup.SignupRequest;
import org.example.day2.core.dto.auth.signup.SignupResponse;
import org.example.day2.core.exception.UnknownException;
import org.example.day2.core.exception.auth.UserExistedException;
import org.example.day2.core.model.user.User;
import org.example.day2.core.repository.user.UserRepository;
import org.example.day2.core.service.auth.AuthService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtHelper jwtHelper;

    @Override
    public SignupResponse signUp(@NotNull SignupRequest request) throws UserExistedException, UnknownException {
        if (userRepository.findByIdentity(request.email()).isPresent()) throw new UserExistedException();
        var encodedPassword = passwordEncoder.encode(request.password());
        userRepository.save(User.from(request, encodedPassword));
        return userRepository.findByIdentity(request.email())
                .map(SignupResponse::from)
                .orElseThrow(UnknownException::new);
    }

    @Override
    public LoginResponse login(LoginRequest request) throws UnknownException {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var token = jwtHelper.generateToken(request.email());
        return userRepository.findByIdentity(request.email())
                .map(user -> LoginResponse.from(user, token))
                .orElseThrow(UnknownException::new);
    }
}
