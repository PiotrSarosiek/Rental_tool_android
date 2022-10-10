package com.highcode.Rental_tool.service;

import com.highcode.Rental_tool.dto.CreateTenantInvitationDTO;
import com.highcode.Rental_tool.dto.TenantInvitationDTO;
import com.highcode.Rental_tool.persistance.dao.ITenantInvitationDao;
import com.highcode.Rental_tool.persistance.entity.Tenant;
import com.highcode.Rental_tool.persistance.entity.TenantInvitation;
import com.highcode.Rental_tool.persistance.entity.User;
import com.highcode.Rental_tool.persistance.enumerated.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenantInvitationService {

    private final ITenantInvitationDao dao;

    private final UserService userService;

    private final TenantService tenantService;

    public List<TenantInvitationDTO> getAllByUserIdAndAccepted(Long userId, Boolean accepted) {
        return dao.getAllByUserIdAndAccepted(userId, accepted).stream()
                .map(TenantInvitationDTO::from)
                .collect(Collectors.toList());
    }

    public List<TenantInvitationDTO> getAllByLandlordId(Long landlordId) {
        return dao.getAllByLandlordId(landlordId).stream()
                .map(TenantInvitationDTO::from)
                .collect(Collectors.toList());
    }

    public TenantInvitationDTO createInvitation(CreateTenantInvitationDTO createTenantInvitationDTO) {
        User user = userService.findByEmailAndRole(createTenantInvitationDTO.getEmail(), UserRole.TENANT);
        User landlord = userService.findById(createTenantInvitationDTO.getLandlordId());
        Tenant tenant = tenantService.findById(createTenantInvitationDTO.getTenantId());
        return TenantInvitationDTO.from(dao.save(TenantInvitation.from(user, landlord, tenant)));
    }

    public TenantInvitationDTO updateStatus(TenantInvitation tenantInvitation, Boolean accepted){
        tenantInvitation.setAccepted(accepted);
        if(accepted){
            tenantService.updateUser(tenantInvitation);
        }
        return TenantInvitationDTO.from(dao.save(tenantInvitation));
    }
}
