package org.example.day2.service.auth;

import lombok.RequiredArgsConstructor;
import org.example.day2.repository.user.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDetails getUserDetails(String email) {
        var user = userRepository.getUser(email);
        return user == null
                ? null
                : User.builder()
                .username(user.email())
                .password(user.password())
                .build();
    }
}
