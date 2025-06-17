package com.simplejava.security.exception;

/**
 * Custom exception to handle bad data scenarios.
 * This exception can be used to indicate that the data provided is not valid or does not meet the required criteria.
 */
public class BadDataException extends RuntimeException{
    private final String errorCode;
    private final String errorMessage;

    public BadDataException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
