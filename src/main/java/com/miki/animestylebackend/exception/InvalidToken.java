package com.miki.animestylebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidToken extends ForbiddenException {
    private static final long serialVersionUID = 1L;

    public InvalidToken(String message) {
        super(message);
    }
}
