package ru.fsv67.models.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
    private Long id;
    private String brandName;

    public Brand(String brandName) {
        this.brandName = brandName;
    }
}
