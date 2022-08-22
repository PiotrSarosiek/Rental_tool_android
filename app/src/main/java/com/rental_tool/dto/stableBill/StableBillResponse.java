package com.rental_tool.dto.stableBill;

import com.rental_tool.dto.extraCosts.ExtraCostResponse;

import java.io.Serializable;
import java.util.List;

public class StableBillResponse implements Serializable {
    private long id;
    private String creationDate;
    private String modificationDate;
    private Double administrativeRent;
    private List<ExtraCostResponse> extraCosts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Double getAdministrativeRent() {
        return administrativeRent;
    }

    public void setAdministrativeRent(Double administrativeRent) {
        this.administrativeRent = administrativeRent;
    }

    public List<ExtraCostResponse> getExtraCosts() {
        return extraCosts;
    }

    public void setExtraCosts(List<ExtraCostResponse> extraCosts) {
        this.extraCosts = extraCosts;
    }
}
