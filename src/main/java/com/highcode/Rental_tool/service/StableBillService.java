package com.highcode.Rental_tool.service;

import com.highcode.Rental_tool.dto.StableBillDTO;
import com.highcode.Rental_tool.persistance.dao.IExtraCostDao;
import com.highcode.Rental_tool.persistance.dao.IStableBillDao;
import com.highcode.Rental_tool.persistance.entity.Apartment;
import com.highcode.Rental_tool.persistance.entity.ExtraCost;
import com.highcode.Rental_tool.persistance.entity.StableBill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StableBillService {

    private final IStableBillDao dao;
    private final IExtraCostDao extraCostDao;

    public StableBillDTO update(Apartment apartment, StableBillDTO stableBillDTO){
        StableBill stableBill = dao.findByApartment(apartment);
        Set<ExtraCost> extraCosts = stableBill.getExtraCosts().stream().peek(extraCost -> extraCost.setStableBill(null)).collect(Collectors.toSet());
        extraCostDao.deleteAll(extraCosts);
        stableBill.setExtraCosts(stableBillDTO.getExtraCosts().stream()
                .map(ExtraCost::from)
                .peek(extraCost -> extraCost.setStableBill(stableBill))
                .collect(Collectors.toSet()));
        stableBill.setAdministrativeRent(stableBillDTO.getAdministrativeRent());
        return StableBillDTO.from(dao.save(stableBill));
    }

}
