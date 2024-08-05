package org.example.day2.core.exception.booking;

import org.example.day2.core.utils.Message;

public class RoomUnavailable extends RuntimeException {
    @Override
    public String getMessage() {
        return Message.ROOM_UNAVAILABLE;
    }
}
