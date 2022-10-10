package com.highcode.Rental_tool.security;

import com.highcode.Rental_tool.persistance.entity.User;
import com.highcode.Rental_tool.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByEmail(s);
        if(user == null) throw new UsernameNotFoundException("user not found");

        if(request.getSession().getAttribute("user") != null){
            request.getSession().removeAttribute("user");
        }
        request.getSession().setAttribute("user",user);

        return new UserDetailsImpl(user);
    }
}
