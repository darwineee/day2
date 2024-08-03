package org.example.day2.dto.wrapper;

public record ErrorRsp<T>(
        int errCode,
        T body
) {
}
