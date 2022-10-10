package com.highcode.Rental_tool.web.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class DataUniquenessException extends RentalToolException {

    private final String entity;

    private final String property;

    public DataUniquenessException(String entity, String property) {
        setExceptionType(ExceptionType.DATA_UNIQUENESS_EXCEPTION);
        setStatus(HttpStatus.CONFLICT);
        this.entity = entity;
        this.property = property;
    }


    @Override
    public void setBody(Map<String, Object> body) {
        super.setBody(body);
        body.put("entity", entity);
        body.put("property", property);
    }

}
