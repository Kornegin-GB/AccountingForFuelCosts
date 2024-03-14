package ru.fsv67.controllers.itinerarySheet;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
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
import ru.fsv67.models.itinerarySheet.FuelRecordView;
import ru.fsv67.services.itinerarySheet.FuelBrandService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
@FxmlView("/screens/itinerarySheet/fuel-update-screen.fxml")
public class FuelUpdateScreenController implements Initializable {
    private final FuelBrandService fuelBrandService;
    public FuelRecordView fuelRecordView;
    public FuelRecord fuelRecord;
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
    void onClickExit() {
        fuelRecord = null;
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickSave() {
        fuelRecordView.setRefuelingDate(new SimpleStringProperty(refuelingDatePicker.getEditor().textProperty().getValue()));
        fuelRecordView.setFuelBrand(new SimpleStringProperty(comboFuelBrand.getSelectionModel().getSelectedItem()));
        fuelRecordView.setFuelPrice(new SimpleDoubleProperty(Double.parseDouble(fuelPriceField.getText())));
        fuelRecordView.setFuelSum(new SimpleDoubleProperty(Double.parseDouble(fuelSumField.getText())));
        fuelRecordView.setFuelQuantity(new SimpleDoubleProperty(Double.parseDouble(fuelQuantityField.getText())));

        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void resultCalculate() {
        if (!fuelQuantityField.getText().isEmpty() && !fuelPriceField.getText().isEmpty() && !fuelSumField.getText().isEmpty()) {
            double v = Double.parseDouble(fuelQuantityField.getText()) * Double.parseDouble(fuelPriceField.getText());
            fuelSumField.setText(Double.toString(v));

            double v1 = Double.parseDouble(fuelSumField.getText()) / Double.parseDouble(fuelQuantityField.getText());
            fuelPriceField.setText(Double.toString(v1));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<FuelBrand> fuelBrandList = fuelBrandService.getFuelBrandList();
        comboFuelBrand.setItems(getObservableList(fuelBrandService.mapToStringFuelBrand(fuelBrandList)));

        if (fuelRecordView != null) {
            refuelingDatePicker.getEditor().setText(fuelRecordView.getRefuelingDate().getValue());
            comboFuelBrand.getSelectionModel().select(fuelRecordView.getFuelBrand().getValue());
            fuelPriceField.setText(Double.toString(fuelRecordView.getFuelPrice().getValue()));
            fuelSumField.setText(Double.toString(fuelRecordView.getFuelSum().getValue()));
            fuelQuantityField.setText(Double.toString(fuelRecordView.getFuelQuantity().getValue()));
        }
    }

    private ObservableList<String> getObservableList(List<String> stringList) {
        return FXCollections.observableArrayList(stringList);
    }
}
