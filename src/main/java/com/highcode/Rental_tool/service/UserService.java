package com.highcode.Rental_tool.service;

import com.highcode.Rental_tool.dto.UserDTO;
import com.highcode.Rental_tool.dto.UserRegisterDTO;
import com.highcode.Rental_tool.persistance.dao.IUserDao;
import com.highcode.Rental_tool.persistance.entity.User;
import com.highcode.Rental_tool.persistance.enumerated.UserRole;
import com.highcode.Rental_tool.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserDao dao;

    private final PasswordEncoder passwordEncoder;

    public User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            UserDetailsImpl springSecurityUser = (UserDetailsImpl) principal;
            return springSecurityUser.getUser();
        }
        return null;
    }

    public User findByEmail(String email){
        return dao.findByEmail(email);
    }

    public User findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public User findByEmailAndRole(String email, UserRole userRole){
        return dao.findByEmailAndRole(email, userRole).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public UserDTO create(UserRegisterDTO userRegisterDTO){
        return UserDTO.from(dao.save(User.from(userRegisterDTO, passwordEncoder)));
    }

    public void update(User user){
        dao.save(user);
    }

}
