package ru.fsv67.models.itinerarySheet;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@EqualsAndHashCode(callSuper = true)
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuelRecordView extends RecursiveTreeObject<FuelRecordView> {
    private LongProperty id;
    private LongProperty numberLine;
    private StringProperty refuelingDate;
    private StringProperty fuelBrand;
    private DoubleProperty fuelQuantity;
    private DoubleProperty fuelPrice;
    private DoubleProperty fuelSum;
    private LongProperty itinerarySheetId;

    public FuelRecordView(Long id, LocalDate refuelingDate, String fuelBrand, Double fuelQuantity,
                          Double fuelPrice,
                          Double fuelSum) {
        this.id = new SimpleLongProperty(id);
        this.refuelingDate = new SimpleStringProperty(refuelingDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        this.fuelBrand = new SimpleStringProperty(fuelBrand);
        this.fuelQuantity = new SimpleDoubleProperty(fuelQuantity);
        this.fuelPrice = new SimpleDoubleProperty(fuelPrice);
        this.fuelSum = new SimpleDoubleProperty(fuelSum);
    }

    public FuelRecordView(LocalDate refuelingDate, String fuelBrand, Double fuelQuantity,
                          Double fuelPrice,
                          Double fuelSum) {
        this.refuelingDate = new SimpleStringProperty(refuelingDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        this.fuelBrand = new SimpleStringProperty(fuelBrand);
        this.fuelQuantity = new SimpleDoubleProperty(fuelQuantity);
        this.fuelPrice = new SimpleDoubleProperty(fuelPrice);
        this.fuelSum = new SimpleDoubleProperty(fuelSum);
    }

    public FuelRecordView(Long id, Long numberLine, LocalDate refuelingDate, String fuelBrand, Double fuelQuantity,
                          Double fuelPrice,
                          Double fuelSum) {
        this.id = new SimpleLongProperty(id);
        this.numberLine = new SimpleLongProperty(numberLine);
        this.refuelingDate = new SimpleStringProperty(refuelingDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        this.fuelBrand = new SimpleStringProperty(fuelBrand);
        this.fuelQuantity = new SimpleDoubleProperty(fuelQuantity);
        this.fuelPrice = new SimpleDoubleProperty(fuelPrice);
        this.fuelSum = new SimpleDoubleProperty(fuelSum);
    }
}
