package com.rental_tool.dto.stableBill;

import com.rental_tool.dto.extraCosts.ExtraCostDTO;
import com.rental_tool.dto.extraCosts.ExtraCostResponse;

import java.io.Serializable;
import java.util.List;

public class StableBillRequest implements Serializable {

    private Double administrativeRent;
    private List<ExtraCostDTO> extraCosts;

    public Double getAdministrativeRent() {
        return administrativeRent;
    }

    public void setAdministrativeRent(Double administrativeRent) {
        this.administrativeRent = administrativeRent;
    }

    public StableBillRequest(Double administrativeRent, List<ExtraCostDTO> extraCosts) {
        this.administrativeRent = administrativeRent;
        this.extraCosts = extraCosts;
    }

    public List<ExtraCostDTO> getExtraCosts() {
        return extraCosts;
    }

    public void setExtraCosts(List<ExtraCostDTO> extraCosts) {
        this.extraCosts = extraCosts;
    }
}
