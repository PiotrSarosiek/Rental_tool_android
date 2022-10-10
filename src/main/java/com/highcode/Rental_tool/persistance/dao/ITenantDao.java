package com.highcode.Rental_tool.persistance.dao;

import com.highcode.Rental_tool.persistance.entity.Apartment;
import com.highcode.Rental_tool.persistance.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITenantDao extends JpaRepository<Tenant, Long> {
    List<Tenant> getAllByApartment(Apartment apartment);
}
