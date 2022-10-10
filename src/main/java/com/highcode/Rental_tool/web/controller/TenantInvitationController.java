package com.highcode.Rental_tool.web.controller;

import com.highcode.Rental_tool.dto.CreateTenantInvitationDTO;
import com.highcode.Rental_tool.dto.TenantInvitationDTO;
import com.highcode.Rental_tool.persistance.entity.TenantInvitation;
import com.highcode.Rental_tool.persistance.entity.User;
import com.highcode.Rental_tool.service.TenantInvitationService;
import com.highcode.Rental_tool.util.RestPreconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenant_invitation")
@SessionAttributes({"user"})
@RequiredArgsConstructor
public class TenantInvitationController {

    private final TenantInvitationService tenantInvitationService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LANDLORD', 'TENANT')")
    @GetMapping("/user")
    public List<TenantInvitationDTO> getAllByUser(@SessionAttribute(name = "user") User user) {
        return tenantInvitationService.getAllByUserIdAndAccepted(user.getId(), null);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LANDLORD', 'TENANT')")
    @GetMapping("/landlord")
    public List<TenantInvitationDTO> getAllByLandlord(@SessionAttribute(name = "user") User user) {
        return tenantInvitationService.getAllByLandlordId(user.getId());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','LANDLORD')")
    @PostMapping
    public TenantInvitationDTO create(@SessionAttribute(name = "user") User user,
                                   @RequestBody CreateTenantInvitationDTO createTenantInvitationDTO) {
        return tenantInvitationService.createInvitation(createTenantInvitationDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TENANT')")
    @PutMapping(value = "/{id}")
    public TenantInvitationDTO markInvitation(@SessionAttribute(name = "user") User user,
                                           @PathVariable("id") TenantInvitation tenantInvitation,
                                           @RequestParam("accepted") Boolean accepted) {
        RestPreconditions.checkFound(tenantInvitation);
        return tenantInvitationService.updateStatus(tenantInvitation, accepted);
    }

}
