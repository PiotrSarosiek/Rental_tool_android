package com.highcode.Rental_tool.web.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class RentalToolException extends RuntimeException {

    private ExceptionType exceptionType;

    private HttpStatus status;

    public RentalToolException(ExceptionType exceptionType, HttpStatus status) {
        this.exceptionType = exceptionType;
        this.status = status;
    }

    public RentalToolException() {

    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setBody(Map<String, Object> body){}
}
