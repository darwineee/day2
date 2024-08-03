package org.example.day2.exception.auth;

import org.example.day2.utils.Message;

public class UserNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return Message.ERR_USER_NOT_FOUND;
    }
}
