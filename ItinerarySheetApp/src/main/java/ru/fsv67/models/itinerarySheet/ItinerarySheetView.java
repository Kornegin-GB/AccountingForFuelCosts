package ru.fsv67.models.itinerarySheet;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Component
@RequiredArgsConstructor
@Data
public class ItinerarySheetView extends RecursiveTreeObject<ItinerarySheetView> {
    private LongProperty id;
    private LongProperty numberLine;
    private StringProperty documentNumber;
    private StringProperty departureDate;
    private StringProperty returnDate;
    private StringProperty car;
    private IntegerProperty exitOdometer;
    private IntegerProperty returnOdometer;
    private StringProperty driver;
    private DoubleProperty initialBalance;
    private DoubleProperty fuelConsumption;
    private DoubleProperty finalBalance;
    private ListProperty<FuelRecordView> fuelRecordList;
    private ListProperty<AddressView> addressList;

    public ItinerarySheetView(Long id, Long numberLine, String documentNumber, LocalDateTime departureDate, LocalDateTime returnDate,
                              String car, Integer exitOdometer, Integer returnOdometer, String driver,
                              Double initialBalance, Double fuelConsumption, Double finalBalance,
                              List<FuelRecordView> fuelRecordList, List<AddressView> addressList) {
        this.id = new SimpleLongProperty(id);
        this.numberLine = new SimpleLongProperty(numberLine);
        this.documentNumber = new SimpleStringProperty(documentNumber);
        this.departureDate =
                new SimpleStringProperty(departureDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        this.returnDate = new SimpleStringProperty(returnDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        this.car = new SimpleStringProperty(car);
        this.exitOdometer = new SimpleIntegerProperty(exitOdometer);
        this.returnOdometer = new SimpleIntegerProperty(returnOdometer);
        this.driver = new SimpleStringProperty(driver);
        this.initialBalance = new SimpleDoubleProperty(initialBalance);
        this.fuelConsumption = new SimpleDoubleProperty(fuelConsumption);
        this.finalBalance = new SimpleDoubleProperty(finalBalance);
        this.fuelRecordList = new SimpleListProperty<>(FXCollections.observableArrayList(fuelRecordList));
        this.addressList = new SimpleListProperty<>(FXCollections.observableArrayList(addressList));
    }
}
