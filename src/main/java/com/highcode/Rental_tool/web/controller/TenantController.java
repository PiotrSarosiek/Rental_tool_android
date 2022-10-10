package com.highcode.Rental_tool.web.controller;

import com.highcode.Rental_tool.dto.TenantDTO;
import com.highcode.Rental_tool.persistance.entity.Apartment;
import com.highcode.Rental_tool.persistance.entity.User;
import com.highcode.Rental_tool.service.TenantService;
import com.highcode.Rental_tool.util.RestPreconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/apartment/{apartment_id}/tenant")
@SessionAttributes({"user"})
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LANDLORD')")
    @GetMapping
    public List<TenantDTO> getAll(@SessionAttribute(name = "user") User user,
                               @PathVariable("apartment_id") Apartment apartment) {
        RestPreconditions.checkFound(apartment);
        return tenantService.getAllByApartment(apartment);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','LANDLORD')")
    @PostMapping
    public TenantDTO create(@SessionAttribute(name = "user") User user,
                         @PathVariable("apartment_id") Apartment apartment,
                         @RequestBody @Valid TenantDTO tenantDTO){
        return tenantService.create(tenantDTO, apartment);
    }

}
