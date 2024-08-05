package org.example.day2.core.annotation;

import java.time.LocalDateTime;

public interface ValidationDateRange {
    LocalDateTime getStartDate();
    LocalDateTime getEndDate();
}
