package org.example.day2.controller;

import org.example.day2.dto.wrapper.ErrorRsp;
import org.example.day2.exception.UnknownException;
import org.example.day2.utils.ErrCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRsp<Map<String, String>>> handle(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String message = fieldError.getDefaultMessage();
            String field = fieldError.getField();
            errors.put(field, message);
        });
        var body = new ErrorRsp<>(ErrCode.FIELD_VALIDATION, errors);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<ErrorRsp<String>> handle(UnknownException ex) {
        var body = new ErrorRsp<>(ErrCode.OTHER, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }
}
