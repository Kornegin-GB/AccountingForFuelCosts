package ru.fsv67.dto.car;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Сущность бренд автомобиля")
public class Brand {
    @Schema(description = "Идентификатор бренда автомобиля")
    private Long id;
    @Schema(description = "Название бренда автомобиля")
    private String brandName;
}
