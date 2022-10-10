package com.highcode.Rental_tool.web.controller;

import com.highcode.Rental_tool.dto.UserDTO;
import com.highcode.Rental_tool.dto.UserRegisterDTO;
import com.highcode.Rental_tool.persistance.entity.User;
import com.highcode.Rental_tool.service.ApartmentService;
import com.highcode.Rental_tool.service.UserService;
import com.highcode.Rental_tool.util.JavaMailUtil;
import com.highcode.Rental_tool.util.RestPreconditions;
import com.highcode.Rental_tool.web.exception.DataUniquenessException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/users")
@SessionAttributes({"user"})
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JavaMailUtil javaMailUtil;
    private final ApartmentService apartmentService;

    @GetMapping(value = "/current")
    public User currentUser(@SessionAttribute(name = "user") User user){
        return user;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LANDLORD')")
    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable("id") User user){
        RestPreconditions.checkFound(user);
        return user;
    }

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public UserDTO register(@RequestBody @Valid final UserRegisterDTO userRegisterDTO){
        if (userService.findByEmail(userRegisterDTO.getEmail()) != null){
            throw new DataUniquenessException("Users", "email");
        }
        return userService.create(userRegisterDTO);
    }

    @PostMapping("/generate_summary")
    @PreAuthorize("hasAuthority('LANDLORD')")
    public void generateSummary(@SessionAttribute(name = "user") User user) throws IOException {
        File file = apartmentService.generateSummaryFile(user);
        String fileName = String.format("rental_tool_summary_%s.csv",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        javaMailUtil.sendSummaryMail(user, fileName, FileUtils.readFileToByteArray(file));
    }

}
