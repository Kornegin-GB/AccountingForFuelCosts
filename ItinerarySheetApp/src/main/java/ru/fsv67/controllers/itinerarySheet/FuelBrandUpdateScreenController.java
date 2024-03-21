package ru.fsv67.controllers.itinerarySheet;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.fsv67.models.itinerarySheet.FuelBrand;
import ru.fsv67.models.itinerarySheet.FuelBrandView;
import ru.fsv67.services.itinerarySheet.FuelBrandService;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("/screens/itinerarySheet/fuel-brand-update.fxml")
@RequiredArgsConstructor
public class FuelBrandUpdateScreenController implements Initializable {
    private final FuelBrandService fuelBrandService;
    FuelBrandView fuelBrandView;

    @FXML
    private JFXTextField labelBrandName;

    @FXML
    void exitForm() {
        fuelBrandView = null;
        Stage stage = (Stage) labelBrandName.getScene().getWindow();
        stage.close();
    }

    @FXML
    void updateBrand() {
        FuelBrand fuelBrand = new FuelBrand(
                fuelBrandView.getId().longValue(),
                labelBrandName.getText()
        );
        fuelBrandService.updateFuelBrand(fuelBrand);
        exitForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (fuelBrandView != null) {
            labelBrandName.setText(fuelBrandView.getFuelBrandName().getValue());
        }
    }
}
