package ru.fsv67.dto.car;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Сущность модель автомобиля")
public class Model {
    @Schema(description = "Идентификатор модели автомобиля")
    private Long id;

    @Schema(description = "Название модели автомобиля")
    private String modelName;

    @Schema(description = "Бренд автомобиля")
    private Brand carBrand;
}
