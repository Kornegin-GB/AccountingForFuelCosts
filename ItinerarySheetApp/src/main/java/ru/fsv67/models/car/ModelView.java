package ru.fsv67.models.car;

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
public class ModelView extends RecursiveTreeObject<ModelView> {
    private LongProperty id;
    private LongProperty numberLine;
    private StringProperty modelName;
    private StringProperty nameBrand;

    public ModelView(Long id, Long numberLine, String modelName, String nameBrand) {
        this.id = new SimpleLongProperty(id);
        this.numberLine = new SimpleLongProperty(numberLine);
        this.modelName = new SimpleStringProperty(modelName);
        this.nameBrand = new SimpleStringProperty(nameBrand);
    }
}
