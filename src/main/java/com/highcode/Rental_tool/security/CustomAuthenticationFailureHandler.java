package com.highcode.Rental_tool.security;

import com.highcode.Rental_tool.web.exception.UserDisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        if(exception.getCause() instanceof UserDisabledException) {
            UserDisabledException originalException = (UserDisabledException) exception.getCause();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"reason\":\"USER_DISABLED\"," +
                    "\"reasonDescription\": \""+originalException.getMessage()+"\"}");
            response.setHeader("Content-Type","application/json");
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
