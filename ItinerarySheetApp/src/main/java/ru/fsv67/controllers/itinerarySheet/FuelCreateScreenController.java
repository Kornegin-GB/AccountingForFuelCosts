package ru.fsv67.controllers.itinerarySheet;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.fsv67.models.itinerarySheet.FuelBrand;
import ru.fsv67.models.itinerarySheet.FuelRecord;
import ru.fsv67.services.itinerarySheet.FuelBrandService;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
@FxmlView("/screens/itinerarySheet/fuel-create-screen.fxml")
public class FuelCreateScreenController implements Initializable {
    private final FuelBrandService fuelBrandService;
    public FuelRecord fuelRecord;
    public LocalDate refuelingDate;
    public List<FuelBrand> fuelBrandList;

    @FXML
    private JFXComboBox<String> comboFuelBrand;

    @FXML
    private JFXButton exitButton;

    @FXML
    private JFXTextField fuelPriceField;

    @FXML
    private JFXTextField fuelQuantityField;

    @FXML
    private JFXTextField fuelSumField;

    @FXML
    private DatePicker refuelingDatePicker;

    @FXML
    public void resultCalculate() {
        if (!fuelQuantityField.getText().isEmpty() && (!fuelSumField.getText().isEmpty() || !fuelPriceField.getText().isEmpty())) {
            if (fuelSumField.getText().isEmpty() && !fuelPriceField.getText().isEmpty() &&
                    fuelQuantityField.getText().matches("[0-9.]+") && fuelPriceField.getText().matches("[0-9.]+")) {
                double sum =
                        Double.parseDouble(fuelQuantityField.getText()) * Double.parseDouble(fuelPriceField.getText());
                fuelSumField.setText(Double.toString(sum));
            }
            if (!fuelSumField.getText().isEmpty() && fuelPriceField.getText().isEmpty()
                    && fuelSumField.getText().matches("[0-9.]+") && fuelQuantityField.getText().matches("[0-9.]+")) {
                double price =
                        Double.parseDouble(fuelSumField.getText()) / Double.parseDouble(fuelQuantityField.getText());
                fuelPriceField.setText(Double.toString(price));
            }
        }
    }

    @FXML
    public void onClickExit() {
        fuelRecord = null;
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onClickSave() {
        if (comboFuelBrand.getSelectionModel().getSelectedItem() != null && !fuelQuantityField.getText().isEmpty()
                && !fuelPriceField.getText().isEmpty() && !fuelSumField.getText().isEmpty() &&
                fuelQuantityField.getText().matches("[0-9.]+") && fuelPriceField.getText().matches("[0-9.]+")
                && fuelSumField.getText().matches("[0-9.]+")) {
            fuelRecord = new FuelRecord(
                    refuelingDatePicker.getValue(),
                    getFuelBrand(),
                    Double.parseDouble(fuelQuantityField.getText()),
                    Double.parseDouble(fuelPriceField.getText()),
                    Double.parseDouble(fuelSumField.getText())
            );
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        }
    }

    public FuelBrand getFuelBrand() {
        String selectedItem = comboFuelBrand.getSelectionModel().getSelectedItem();
        return fuelBrandList.stream()
                .filter(fuelBrand -> Objects.equals(fuelBrand.getFuelBrandName(), selectedItem))
                .findFirst().orElseThrow(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fuelBrandList = fuelBrandService.getFuelBrandList();
        if (refuelingDate != null) {
            refuelingDatePicker.setValue(refuelingDate);
        }
        comboFuelBrand.setItems(getObservableList(fuelBrandService.mapToStringFuelBrand(fuelBrandList)));
    }

    private ObservableList<String> getObservableList(List<String> stringList) {
        return FXCollections.observableArrayList(stringList);
    }
}
