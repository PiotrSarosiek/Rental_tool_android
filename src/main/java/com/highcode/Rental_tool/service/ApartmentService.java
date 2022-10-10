package com.highcode.Rental_tool.service;

import com.highcode.Rental_tool.dto.ApartmentDTO;
import com.highcode.Rental_tool.dto.CreateApartmentDTO;
import com.highcode.Rental_tool.dto.StableBillDTO;
import com.highcode.Rental_tool.dto.TenantDTO;
import com.highcode.Rental_tool.persistance.dao.IApartmentDao;
import com.highcode.Rental_tool.persistance.entity.*;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApartmentService {

    private final IApartmentDao dao;

    public List<ApartmentDTO> findAll() {
        return dao.findAll().stream()
                .map(ApartmentDTO::from)
                .collect(Collectors.toList());
    }

    public List<ApartmentDTO> findByLandlord(User user) {
        return dao.findByLandlord(user).stream()
                .map(ApartmentDTO::from)
                .collect(Collectors.toList());
    }

    public ApartmentDTO findByUserAssigned(User user){
        ApartmentDTO apartmentDTO = dao.findByUserAssignedToTenant(user)
                .map(ApartmentDTO::from)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        Set<TenantDTO> tenants = apartmentDTO.getTenants().stream()
                .filter(tenantDTO -> tenantDTO.getUser() != null && tenantDTO.getUser().getId().equals(user.getId()))
                .collect(Collectors.toSet());

        StableBillDTO stableBillDTO = StableBillDTO.builder()
                .administrativeRent(Math.round(apartmentDTO.getStableBill().getAdministrativeRent()/apartmentDTO.getTenants().size() * 100.0) / 100.0)
                .extraCosts(apartmentDTO.getStableBill().getExtraCosts().stream()
                        .peek(extraCostDTO -> extraCostDTO.setAmount((double) (Math.round(extraCostDTO.getAmount() / apartmentDTO.getTenants().size() * 100) / 100)))
                        .collect(Collectors.toSet()))
                .id(apartmentDTO.getStableBill().getId())
                .build();

        apartmentDTO.setStableBill(stableBillDTO);
        apartmentDTO.setTenants(tenants);
        return apartmentDTO;
    }

    public ApartmentDTO createApartment(CreateApartmentDTO createApartmentDTO, User landlord) {
        Apartment apartment = Apartment.from(createApartmentDTO, landlord);
        StableBill stableBill = new StableBill();
        stableBill.setAdministrativeRent(0.0);
        stableBill.setApartment(apartment);
        apartment.setStableBill(stableBill);
        return ApartmentDTO.from(dao.save(apartment));
    }

    public void deleteById(Long apartmentId){
        dao.deleteById(apartmentId);
    }

    public File generateSummaryFile(User user) throws IOException {
        List<Apartment> apartments = dao.findByLandlord(user);
        File file = File.createTempFile("summary", ".csv");
        FileWriter fileWriter = new FileWriter(file);
        CSVWriter writer = new CSVWriter(fileWriter);
        apartments.forEach(apartment -> {
            writer.writeNext(new String[]{"Apartment"});
            writer.writeNext(new String[]{"address:", apartment.getAddress()});
            writer.writeNext(new String[]{});

            writer.writeNext(new String[]{"Stable Bill"});
            List<String> headers = new ArrayList<>();
            headers.add("administrative_rent");
            apartment.getStableBill().getExtraCosts().forEach(extraCost -> headers.add(extraCost.getName()));
            writer.writeNext(headers.toArray(new String[0]));

            List<String> values = new ArrayList<>();
            values.add(apartment.getStableBill().getAdministrativeRent().toString());
            apartment.getStableBill().getExtraCosts().forEach(extraCost -> values.add(extraCost.getAmount().toString()));
            writer.writeNext(values.toArray(new String[0]));

            writer.writeNext(new String[]{});

            AtomicInteger atomicInteger = new AtomicInteger(1);
            apartment.getTenants().forEach(tenant -> {
                writer.writeNext(new String[]{"Tenant "+atomicInteger.getAndIncrement()});
                List<String> headers1 = new ArrayList<>();
                headers1.add("summary");
                headers1.add("rent");
                tenant.getExtraCosts().forEach(extraCost -> headers1.add(extraCost.getName()));
                writer.writeNext(headers1.toArray(new String[0]));

                double summary = calculateDividedCosts(apartment)+calculateTenantCost(tenant);
                List<String> values1 = new ArrayList<>();
                values1.add(Double.toString(summary));
                values1.add(tenant.getRent().toString());
                tenant.getExtraCosts().forEach(extraCost -> values1.add(extraCost.getAmount().toString()));
                writer.writeNext(values1.toArray(new String[0]));

                writer.writeNext(new String[]{"email", "name", "surname", "phone"});
                if(tenant.getUser()!=null)
                    writer.writeNext(new String[]{tenant.getUser().getEmail(), tenant.getUser().getName(), tenant.getUser().getSurname(), tenant.getUser().getPhone()});
                else
                    writer.writeNext(new String[]{});
                writer.writeNext(new String[]{});

            });
            writer.writeNext(new String[]{});
            writer.writeNext(new String[]{});
            writer.writeNext(new String[]{});

        });
        writer.close();
        return file;

    }

    private Double calculateDividedCosts(Apartment apartment){
        Double sum = apartment.getStableBill().getAdministrativeRent();
        sum += apartment.getStableBill().getExtraCosts().stream().mapToDouble(ExtraCost::getAmount).sum();
        return Math.round(sum/apartment.getTenants().size() * 100.0) / 100.0;
    }

    private Double calculateTenantCost(Tenant tenant){
        Double sum = tenant.getRent();
        sum += tenant.getExtraCosts().stream().mapToDouble(ExtraCost::getAmount).sum();
        return sum;
    }

}
