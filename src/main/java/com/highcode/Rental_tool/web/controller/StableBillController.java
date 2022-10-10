package com.highcode.Rental_tool.web.controller;

import com.highcode.Rental_tool.dto.StableBillDTO;
import com.highcode.Rental_tool.persistance.entity.Apartment;
import com.highcode.Rental_tool.persistance.entity.User;
import com.highcode.Rental_tool.service.StableBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/apartment/{apartment_id}/stable_bill")
@SessionAttributes({"user"})
@RequiredArgsConstructor
public class StableBillController {

    private final StableBillService stableBillService;

    @PreAuthorize("hasAnyAuthority('ADMIN','LANDLORD')")
    @PutMapping
    public StableBillDTO update(@SessionAttribute(name = "user") User user,
                             @PathVariable("apartment_id") Apartment apartment,
                             @RequestBody @Valid StableBillDTO stableBillDTO){
        return stableBillService.update(apartment, stableBillDTO);
    }
}
