package com.highcode.Rental_tool.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.highcode.Rental_tool.persistance.entity.User;
import com.highcode.Rental_tool.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class MySavedRequestAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;

    private final RequestCache requestCache = new HttpSessionRequestCache();

    private final ObjectMapper jacksonObjectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException {

        updateLastLoginDate();

        UserDetailsImpl currentUserDetails = (UserDetailsImpl) authentication.getPrincipal();

        response.getWriter().write(jacksonObjectMapper.writeValueAsString(currentUserDetails.getUser()));
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest == null) {
            clearAuthenticationAttributes(request);
            return;
        }
        String targetUrlParam = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl()
                || (targetUrlParam != null
                && StringUtils.hasText(request.getParameter(targetUrlParam)))) {
            requestCache.removeRequest(request, response);
            clearAuthenticationAttributes(request);
            return;
        }

        clearAuthenticationAttributes(request);
    }

    private void updateLastLoginDate() {
        User user = userService.getCurrentUser();
        user.setLastUsageDate(OffsetDateTime.now());
        userService.update(user);
    }

}
