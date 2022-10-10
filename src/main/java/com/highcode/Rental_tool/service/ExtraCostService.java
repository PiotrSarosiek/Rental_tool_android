package com.highcode.Rental_tool.service;

import com.highcode.Rental_tool.persistance.dao.IExtraCostDao;
import com.highcode.Rental_tool.persistance.entity.ExtraCost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtraCostService {

    private final IExtraCostDao dao;

    public void deleteAll(List<ExtraCost> extraCosts) {
        dao.deleteAll(extraCosts);
    }
}
