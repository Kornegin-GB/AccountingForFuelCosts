package ru.fsv67.models.car;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class CarView extends RecursiveTreeObject<CarView> {
    private LongProperty id;
    private LongProperty numberLine;
    private StringProperty registrationNumber;
    private StringProperty vin;
    private DoubleProperty fuelConsumptionRate;
    private IntegerProperty seriesItinerarySheet;
    private IntegerProperty initialOdometer;
    private StringProperty carModel;
    private StringProperty carBrand;

    public CarView(Long id, Long numberLine, String registrationNumber, String vin, Double fuelConsumptionRate,
                   Integer seriesItinerarySheet,
                   Integer initialOdometer, String carModel, String carBrand) {
        this.id = new SimpleLongProperty(id);
        this.numberLine = new SimpleLongProperty(numberLine);
        this.registrationNumber = new SimpleStringProperty(registrationNumber);
        this.vin = new SimpleStringProperty(vin);
        this.fuelConsumptionRate = new SimpleDoubleProperty(fuelConsumptionRate);
        this.seriesItinerarySheet = new SimpleIntegerProperty(seriesItinerarySheet);
        this.initialOdometer = new SimpleIntegerProperty(initialOdometer);
        this.carModel = new SimpleStringProperty(carModel);
        this.carBrand = new SimpleStringProperty(carBrand);
    }
}
