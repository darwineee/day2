package org.example.day2.core.exception.user;

import org.example.day2.core.utils.Message;

public class UserNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return Message.ERR_USER_NOT_FOUND;
    }
}
