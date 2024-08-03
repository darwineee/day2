package org.example.day2.exception;

import org.example.day2.utils.Message;

public class UnknownException extends Exception {
    @Override
    public String getMessage() {
        return Message.ERR_INTERNAL;
    }
}
