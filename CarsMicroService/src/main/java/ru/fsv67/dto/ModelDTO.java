package ru.fsv67.dto;

import lombok.Data;
import ru.fsv67.models.CarBrand;

@Data
public class ModelDTO {
    private Long id;
    private String modelName;
    private CarBrand carBrand;
}
