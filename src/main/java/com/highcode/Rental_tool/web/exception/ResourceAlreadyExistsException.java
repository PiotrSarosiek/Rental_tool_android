package com.highcode.Rental_tool.web.exception;

public final class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException() {
        super();
    }

    public ResourceAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ResourceAlreadyExistsException(final String message) {
        super(message);
    }

    public ResourceAlreadyExistsException(final Throwable cause) {
        super(cause);
    }

}
