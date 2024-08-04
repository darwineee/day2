package org.example.day2.service.auth;

import lombok.RequiredArgsConstructor;
import org.example.day2.core.repository.user.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByIdentity(username)
                .map(user -> User.builder()
                        .username(user.email())
                        .password(user.password())
                        .disabled(!user.active())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
