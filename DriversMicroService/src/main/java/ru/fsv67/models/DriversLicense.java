package ru.fsv67.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drivers_license")
@Schema(description = "Сущность водительское удостоверение")
public class DriversLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор водительского  удостоверения")
    private Long id;

    @Column(name = "number_drivers_license")
    @Schema(description = "Номер водительского удостоверения")
    private String numberDriversLicense;

    @Column(name = "categories")
    @Schema(description = "Разрешенные категории")
    private String categories;

    @Column(name = "date_of_issue")
    @Schema(description = "Дата выдачи удостоверения")
    private LocalDate dateOfIssue;

    @Column(name = "date_of_validity")
    @Schema(description = "Дата срока действия удостоверения")
    private LocalDate dateOfValidity;

    @Column(name = "is_really")
    @Schema(description = "Признак действительности")
    private Boolean isReally;

    @Column(name = "driver_id")
    @Schema(description = "Идентификатор водителя")
    private Long driverId;

    public DriversLicense(String numberDriversLicense, String categories, LocalDate dateOfIssue,
                          LocalDate dateOfValidity, Boolean isReally) {
        this.numberDriversLicense = numberDriversLicense;
        this.categories = categories;
        this.dateOfIssue = dateOfIssue;
        this.dateOfValidity = dateOfValidity;
        this.isReally = isReally;
    }
}
