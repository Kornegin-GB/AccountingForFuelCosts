package ru.fsv67.models.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {
    private Long id;
    private String modelName;
    private Brand carBrand;

    public Model(String modelName, Brand carBrand) {
        this.modelName = modelName;
        this.carBrand = carBrand;
    }
}
