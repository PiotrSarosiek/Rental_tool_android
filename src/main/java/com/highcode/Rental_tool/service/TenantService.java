package com.highcode.Rental_tool.service;

import com.highcode.Rental_tool.dto.TenantDTO;
import com.highcode.Rental_tool.persistance.dao.ITenantDao;
import com.highcode.Rental_tool.persistance.entity.Apartment;
import com.highcode.Rental_tool.persistance.entity.Tenant;
import com.highcode.Rental_tool.persistance.entity.TenantInvitation;
import com.highcode.Rental_tool.web.exception.MyResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final ITenantDao dao;

    public List<TenantDTO> getAllByApartment(Apartment apartment) {
        return dao.getAllByApartment(apartment).stream().map(TenantDTO::from).collect(Collectors.toList());
    }

    public Tenant findById(Long tenantId) {
        return dao.findById(tenantId).orElseThrow(() -> new MyResourceNotFoundException("tenant does not exists"));
    }

    public TenantDTO create(TenantDTO tenantDTO, Apartment apartment){
        return TenantDTO.from(dao.save(Tenant.from(tenantDTO, apartment)));
    }

    public void updateUser(TenantInvitation tenantInvitation){
        Tenant tenant = tenantInvitation.getTenant();
        tenant.setUser(tenantInvitation.getUser());
        dao.save(tenant);
    }

}
