package com.highcode.Rental_tool.persistance.dao;

import com.highcode.Rental_tool.persistance.entity.Apartment;
import com.highcode.Rental_tool.persistance.entity.StableBill;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IStableBillDao extends JpaRepository<StableBill, Long> {
    StableBill findByApartment(Apartment apartment);
}
