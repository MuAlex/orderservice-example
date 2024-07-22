package org.example.exception;

public class BusinessException extends Exception{

    private String message;

    public BusinessException(String message) {
        super(message);
    }
}
