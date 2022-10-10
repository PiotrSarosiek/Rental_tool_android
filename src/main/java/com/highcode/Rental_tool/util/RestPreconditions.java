package com.highcode.Rental_tool.util;

import com.highcode.Rental_tool.web.exception.MyResourceNotFoundException;
import com.highcode.Rental_tool.web.exception.RentalToolException;

public final class RestPreconditions {
    private RestPreconditions() {
        throw new AssertionError();
    }

    public static <T> T checkFound(final T resource) {
        if (resource == null) {
            throw new MyResourceNotFoundException("resource not found");
        }

        return resource;
    }

    public static <T> T checkNotNull(final T resource) {
        if (resource == null) {
            throw new MyResourceNotFoundException();
        }

        return resource;
    }
}
