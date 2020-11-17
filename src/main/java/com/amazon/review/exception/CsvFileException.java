package com.amazon.review.exception;

public class CsvFileException extends RuntimeException {
    public CsvFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
