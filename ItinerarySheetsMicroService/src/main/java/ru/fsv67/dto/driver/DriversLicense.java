package ru.fsv67.dto.driver;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Водительское удостоверение водителя")
public class DriversLicense {
    @Schema(description = "Идентификатор водительского удостоверения")
    private Long id;

    @Schema(description = "Номер водительского удостоверения")
    private String numberDriversLicense;

    @Schema(description = "Разрешенные категории водительского удостоверения")
    private String categories;

    @Schema(description = "Дата выдачи водительского удостоверения")
    private LocalDate dateOfIssue;

    @Schema(description = "Дата окончания срока действия водительского удостоверения")
    private LocalDate dateOfValidity;
}
