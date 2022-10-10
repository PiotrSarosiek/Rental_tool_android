package com.highcode.Rental_tool.persistance.dao;

import com.highcode.Rental_tool.persistance.entity.ExtraCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExtraCostDao extends JpaRepository<ExtraCost, Long> {
}
