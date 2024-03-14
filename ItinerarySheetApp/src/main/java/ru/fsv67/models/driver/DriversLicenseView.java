package ru.fsv67.models.driver;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class DriversLicenseView extends RecursiveTreeObject<DriversLicenseView> {
    private LongProperty id;
    private LongProperty numberLine;
    private StringProperty numberDriversLicense;
    private StringProperty categories;
    private StringProperty dateOfIssue;
    private StringProperty dateOfValidity;
    private StringProperty isReally;
    private LongProperty driverId;

    public DriversLicenseView(Long id, Long numberLine, String numberDriversLicense, String categories,
                              LocalDate dateOfIssue, LocalDate dateOfValidity, Boolean isReally, Long driverId) {
        this.id = new SimpleLongProperty(id);
        this.numberLine = new SimpleLongProperty(numberLine);
        this.numberDriversLicense = new SimpleStringProperty(numberDriversLicense);
        this.categories = new SimpleStringProperty(categories);
        this.dateOfIssue = new SimpleStringProperty(dateOfIssue.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        this.dateOfValidity =
                new SimpleStringProperty(dateOfValidity.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        if (isReally) {
            this.isReally = new SimpleStringProperty("Действительно");
        } else {
            this.isReally = new SimpleStringProperty("Не действительно");
        }
        this.driverId = new SimpleLongProperty(driverId);
    }
}
