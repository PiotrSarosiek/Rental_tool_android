package com.highcode.Rental_tool.persistance.dao;

import com.highcode.Rental_tool.persistance.entity.TenantInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITenantInvitationDao extends JpaRepository<TenantInvitation, Long> {
    List<TenantInvitation> getAllByUserIdAndAccepted(Long id, Boolean accepted);

    List<TenantInvitation> getAllByLandlordId(Long landlordId);
}
