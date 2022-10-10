package com.highcode.Rental_tool.web.exception;

public final class UserDisabledException extends RuntimeException {

    public UserDisabledException() {
        super();
    }

    public UserDisabledException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserDisabledException(final String message) {
        super(message);
    }

    public UserDisabledException(final Throwable cause) {
        super(cause);
    }

}

