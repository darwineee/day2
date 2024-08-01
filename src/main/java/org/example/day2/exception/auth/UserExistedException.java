package org.example.day2.exception.auth;

import org.example.day2.utils.Message;

public class UserExistedException extends Exception {
    @Override
    public String getMessage() {
        return Message.ERR_USER_EXISTED;
    }
}
