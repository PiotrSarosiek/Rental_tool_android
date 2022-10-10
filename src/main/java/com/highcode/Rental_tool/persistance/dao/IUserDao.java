package com.highcode.Rental_tool.persistance.dao;

import com.highcode.Rental_tool.persistance.entity.User;
import com.highcode.Rental_tool.persistance.enumerated.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface IUserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Optional<User> findByEmailAndRole(String email, UserRole userRole);
}
