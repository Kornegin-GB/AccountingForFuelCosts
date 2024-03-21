package ru.fsv67.controllers.itinerarySheet;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.fsv67.models.itinerarySheet.FuelBrand;
import ru.fsv67.services.itinerarySheet.FuelBrandService;

@Component
@FxmlView("/screens/itinerarySheet/fuel-brand-create.fxml")
@RequiredArgsConstructor
public class FuelBrandCreateScreenController {
    private final FuelBrandService fuelBrandService;
    @FXML
    private JFXTextField labelBrandName;

    @FXML
    void clearForm() {
        labelBrandName.setText("");
    }

    @FXML
    void exitForm() {
        Stage stage = (Stage) labelBrandName.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveBrand() {
        FuelBrand fuelBrand = new FuelBrand(
                labelBrandName.getText()
        );
        fuelBrandService.saveFuelBrand(fuelBrand);
        exitForm();
    }
}
