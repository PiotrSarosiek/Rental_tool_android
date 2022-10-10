package com.highcode.Rental_tool.persistance.dao;

import com.highcode.Rental_tool.persistance.entity.Apartment;
import com.highcode.Rental_tool.persistance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IApartmentDao extends JpaRepository<Apartment, Long> {
    List<Apartment> findByLandlord(User user);

    Apartment findByLandlordAndId(User user, Long id);

    @Query("select a from Apartment a join a.tenants t where t.user=:user")
    Optional<Apartment> findByUserAssignedToTenant(@Param("user") User user);
}
