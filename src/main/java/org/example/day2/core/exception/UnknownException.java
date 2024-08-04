package org.example.day2.core.exception;

import org.example.day2.core.utils.Message;

public class UnknownException extends Exception {
    @Override
    public String getMessage() {
        return Message.ERR_INTERNAL;
    }
}
