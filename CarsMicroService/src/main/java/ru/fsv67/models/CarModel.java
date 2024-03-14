package ru.fsv67.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность модель автомобиля
 */
@Entity
@Table(name = "car_model")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Сущность модель автомобиля")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_model_id")
    @Schema(description = "Идентификатор модель автомобиля")
    private Long id;

    @Column(name = "model_name")
    @Schema(description = "Название модели автомобиля")
    private String modelName;

    @Column(name = "car_brand_id")
    @Schema(description = "Идентификатор бренда автомобиля")
    private Long carBrand;

    public CarModel(String modelName, Long carBrand) {
        this.modelName = modelName;
        this.carBrand = carBrand;
    }
}
