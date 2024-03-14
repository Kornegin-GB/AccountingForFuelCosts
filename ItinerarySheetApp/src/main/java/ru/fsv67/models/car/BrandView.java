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
public class BrandView extends RecursiveTreeObject<BrandView> {
    private LongProperty id;
    private LongProperty numberLine;
    private StringProperty brandName;

    public BrandView(Long id, Long numberLine, String brandName) {
        this.id = new SimpleLongProperty(id);
        this.numberLine = new SimpleLongProperty(numberLine);
        this.brandName = new SimpleStringProperty(brandName);
    }
}
