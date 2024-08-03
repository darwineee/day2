package org.example.day2.model.user;

import lombok.Builder;

@Builder
public record User(
        int id,
        String email,
        String password,
        boolean active
) {
}

