package com.emnsoft.emnsurvey.exception;

public class InvalidTokenException extends RuntimeException {
    
    static final long serialVersionUID = 1;

    public InvalidTokenException() {
        super("Cannot deserialize token. Invalid token");   
    }
}
