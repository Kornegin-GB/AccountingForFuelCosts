package ru.fsv67.models.itinerarySheet;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Component
@RequiredArgsConstructor
@Data
public class AddressView extends RecursiveTreeObject<AddressView> {
    private LongProperty id;
    private LongProperty numberLine;
    private StringProperty departureAddress;
    private StringProperty destinationAddress;

    public AddressView(Long id, Long numberLine, String departureAddress, String destinationAddress) {
        this.id = new SimpleLongProperty(id);
        this.numberLine = new SimpleLongProperty(numberLine);
        this.departureAddress = new SimpleStringProperty(departureAddress);
        this.destinationAddress = new SimpleStringProperty(destinationAddress);
    }

    public AddressView(Long numberLine, String departureAddress, String destinationAddress) {
        this.numberLine = new SimpleLongProperty(numberLine);
        this.departureAddress = new SimpleStringProperty(departureAddress);
        this.destinationAddress = new SimpleStringProperty(destinationAddress);
    }

    public AddressView(String departureAddress, String destinationAddress) {
        this.departureAddress = new SimpleStringProperty(departureAddress);
        this.destinationAddress = new SimpleStringProperty(destinationAddress);
    }

}
