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
public class FuelBrandView extends RecursiveTreeObject<FuelBrandView> {
    private LongProperty id;
    private LongProperty numberLine;
    private StringProperty fuelBrandName;

    public FuelBrandView(Long numberLine, String fuelBrandName) {
        this.fuelBrandName = new SimpleStringProperty(fuelBrandName);
        this.numberLine = new SimpleLongProperty(numberLine);
    }

    public FuelBrandView(Long id, Long numberLine, String fuelBrandName) {
        this.id = new SimpleLongProperty(id);
        this.numberLine = new SimpleLongProperty(numberLine);
        this.fuelBrandName = new SimpleStringProperty(fuelBrandName);
    }
}
