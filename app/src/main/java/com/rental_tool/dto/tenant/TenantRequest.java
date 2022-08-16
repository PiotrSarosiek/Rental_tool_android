package com.rental_tool.dto.tenant;

import com.rental_tool.dto.extraCosts.ExtraCostDTO;

import java.io.Serializable;
import java.util.List;

public class TenantRequest implements Serializable {

    private Double rent;
    private List<ExtraCostDTO> extraCosts;

    public TenantRequest(Double rent, List<ExtraCostDTO> extraCosts) {
        this.rent = rent;
        this.extraCosts = extraCosts;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public List<ExtraCostDTO> getExtraCosts() {
        return extraCosts;
    }

    public void setExtraCosts(List<ExtraCostDTO> extraCosts) {
        this.extraCosts = extraCosts;
    }
}
