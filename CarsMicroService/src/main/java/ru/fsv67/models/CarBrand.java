package ru.fsv67.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность бренд автомобиля
 */
@Entity
@Table(name = "cars_brand")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Сущность бренд автомобиля")
public class CarBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_brand_id")
    @Schema(description = "Идентификатор бренда автомобиля")
    private Long id;

    @Column(name = "brand_name")
    @Schema(description = "Название бренда автомобиля")
    private String brandName;

    public CarBrand(String brandName) {
        this.brandName = brandName;
    }
}
