package com.sportygroup.jackpot.exception;

public class MandatoryFieldNotFoundException extends RuntimeException {
    public MandatoryFieldNotFoundException(String message) {
        super(message);
    }
}