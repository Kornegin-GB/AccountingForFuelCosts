package ru.fsv67.models.itinerarySheet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuelBrand {
    private Long id;
    private String fuelBrandName;

    public FuelBrand(String fuelBrandName) {
        this.fuelBrandName = fuelBrandName;
    }
}
