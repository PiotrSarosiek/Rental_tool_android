package com.rental_tool.dto.unstableBill;

import com.rental_tool.dto.stableBill.extraCostResponse.ExtraCostResponse;

import java.io.Serializable;
import java.util.List;

public class UnstableBillResponse implements Serializable {
    private int id;
    private String creationDate;
    private String modificationDate;
    private Double water;
    private Double electricity;
    private Double gas;
    private String validityMonth;
    private List<ExtraCostResponse> extraCosts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Double getWater() {
        return water;
    }

    public void setWater(Double water) {
        this.water = water;
    }

    public Double getElectricity() {
        return electricity;
    }

    public void setElectricity(Double electricity) {
        this.electricity = electricity;
    }

    public Double getGas() {
        return gas;
    }

    public void setGas(Double gas) {
        this.gas = gas;
    }

    public String getValidityMonth() {
        return validityMonth;
    }

    public void setValidityMonth(String validityMonth) {
        this.validityMonth = validityMonth;
    }

    public List<ExtraCostResponse> getExtraCosts() {
        return extraCosts;
    }

    public void setExtraCosts(List<ExtraCostResponse> extraCosts) {
        this.extraCosts = extraCosts;
    }
}
