package org.example.day2.service.auth;

import lombok.RequiredArgsConstructor;
import org.example.day2.dto.auth.signup.SignupRequest;
import org.example.day2.dto.auth.signup.SignupResponse;
import org.example.day2.exception.auth.UserExistedException;
import org.example.day2.model.user.User;
import org.example.day2.repository.user.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public SignupResponse signUp(@NotNull SignupRequest request) throws UserExistedException {
        if (userRepository.getUser(request.email()) != null) {
            throw new UserExistedException();
        }
        userRepository.insertUser(
                User.builder()
                        .email(request.email())
                        .password(request.password())
                        .build()
        );
        var user = userRepository.getUser(request.email());
        return SignupResponse.builder()
                .id(user.id())
                .email(user.email())
                .inactive(user.inactive())
                .build();
    }

    public boolean login() {

    }
}
