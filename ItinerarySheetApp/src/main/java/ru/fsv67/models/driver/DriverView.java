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

@Component
@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class DriverView extends RecursiveTreeObject<DriverView> {
    private LongProperty id;
    private LongProperty numberLine;
    private StringProperty fio;
    private StringProperty snils;

    public DriverView(Long id, Long numberLine, String fio, String snils) {
        this.id = new SimpleLongProperty(id);
        this.numberLine = new SimpleLongProperty(numberLine);
        this.fio = new SimpleStringProperty(fio);
        this.snils = new SimpleStringProperty(snils);
    }
}
