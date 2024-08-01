package org.example.day2.service.user;

import lombok.RequiredArgsConstructor;
import org.example.day2.repository.user.UserRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public @NonNull UserDetails getUserDetails(String email) throws UsernameNotFoundException {
        var user = userRepository.getUser(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        } else {
           return User.builder()
                   .username(user.email())
                   .password(user.password())
                   .build();
        }
    }
}
