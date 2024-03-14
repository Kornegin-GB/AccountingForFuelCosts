package ru.fsv67.dto.driver;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Сущность водитель")
public class Driver {
    @Schema(description = "Идентификатор водителя")
    private Long id;

    @Schema(description = "Имя водителя")
    private String firstName;

    @Schema(description = "Фамилия водителя")
    private String lastName;

    @Schema(description = "Отчество водителя")
    private String parentName;

    @Schema(description = "СНИЛС водителя")
    private String snils;

    @Schema(description = "Водительское удостоверение водителя")
    private List<DriversLicense> driversLicense;
}
