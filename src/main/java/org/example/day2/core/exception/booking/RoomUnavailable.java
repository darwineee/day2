package org.example.day2.core.exception.booking;

public class RoomUnavailable extends Exception {

    private final String msg;

    public RoomUnavailable(String message) {
        msg = message;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
