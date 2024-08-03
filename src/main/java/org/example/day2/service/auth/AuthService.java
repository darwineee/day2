package org.example.day2.service.auth;

import lombok.RequiredArgsConstructor;
import org.example.day2.dto.auth.signup.SignupRequest;
import org.example.day2.dto.auth.signup.SignupResponse;
import org.example.day2.exception.UnknownException;
import org.example.day2.exception.auth.UserExistedException;
import org.example.day2.model.user.User;
import org.example.day2.repository.user.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponse signUp(@NotNull SignupRequest request) throws UserExistedException, UnknownException {
        if (userRepository.isUserExist(request.email())) throw new UserExistedException();
        userRepository.insertUser(
                User.builder()
                        .email(request.email())
                        .password(passwordEncoder.encode(request.password()))
                        .active(true)
                        .build()
        );
        return userRepository.getUser(request.email())
                .map(user ->
                        SignupResponse.builder()
                                .id(user.id())
                                .email(user.email())
                                .active(user.active())
                                .build()
                )
                .orElseThrow(UnknownException::new);
    }

//    public boolean login() {
//
//    }
}
