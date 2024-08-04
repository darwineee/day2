package org.example.day2.core.dto.wrapper;

public record ErrorRsp<T>(
        int errCode,
        T meta
) {
}
