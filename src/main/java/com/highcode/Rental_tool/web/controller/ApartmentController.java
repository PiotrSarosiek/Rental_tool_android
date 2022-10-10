package com.highcode.Rental_tool.web.controller;

import com.highcode.Rental_tool.dto.ApartmentDTO;
import com.highcode.Rental_tool.dto.CreateApartmentDTO;
import com.highcode.Rental_tool.persistance.entity.Apartment;
import com.highcode.Rental_tool.persistance.entity.User;
import com.highcode.Rental_tool.persistance.enumerated.UserRole;
import com.highcode.Rental_tool.service.ApartmentService;
import com.highcode.Rental_tool.service.UserService;
import com.highcode.Rental_tool.util.RestPreconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/apartment")
@SessionAttributes({"user"})
@RequiredArgsConstructor
public class ApartmentController {

    private final ApartmentService apartmentService;

    private final UserService userService;


    @PreAuthorize("hasAnyAuthority('ADMIN', 'LANDLORD')")
    @GetMapping
    public List<ApartmentDTO> getAll(@SessionAttribute(name = "user") User user) {
        if (user.getRole().equals(UserRole.LANDLORD))
            return apartmentService.findByLandlord(user);
        return apartmentService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','LANDLORD')")
    @PostMapping
    public ApartmentDTO create(@SessionAttribute(name = "user") User user,
                               @RequestBody @Valid CreateApartmentDTO createApartmentDTO){
        return apartmentService.createApartment(createApartmentDTO, user);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','LANDLORD')")
    @DeleteMapping(value = "/{id}")
    public void delete(@SessionAttribute(name = "user") User user,
                       @PathVariable("id") Apartment apartment) {
        RestPreconditions.checkFound(apartment);
        apartmentService.deleteById(apartment.getId());
    }

    @PreAuthorize("hasAnyAuthority('TENANT')")
    @GetMapping("/assigned_to_tenant")
    public ApartmentDTO getByUserAssigned(@SessionAttribute(name = "user") User user) {
        return apartmentService.findByUserAssigned(user);
    }


}
