package ru.fsv67.controllers.car;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.fsv67.models.car.Brand;
import ru.fsv67.models.car.Car;
import ru.fsv67.models.car.Model;
import ru.fsv67.services.car.BrandService;
import ru.fsv67.services.car.CarService;
import ru.fsv67.services.car.ModelService;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@FxmlView("/screens/car/car-create.fxml")
@RequiredArgsConstructor
public class CarCreateScreenController implements Initializable {
    private final BrandService brandService;
    private final ModelService modelService;
    private final CarService carService;
    List<Brand> listBrand;
    List<Model> listModel;
    @FXML
    private JFXComboBox<String> comboBoxBrand;
    @FXML
    private JFXComboBox<String> comboBoxModel;
    @FXML
    private JFXTextField labelFuel;
    @FXML
    private JFXTextField labelOdometer;
    @FXML
    private JFXTextField labelRegisterNumber;
    @FXML
    private JFXTextField labelSeries;
    @FXML
    private JFXTextField labelVin;

    @FXML
    void clearForm() {
        labelSeries.setText("");
        labelVin.setText("");
        labelRegisterNumber.setText("");
        labelOdometer.setText("");
        labelFuel.setText("");
        comboBoxBrand.getSelectionModel().clearSelection();
        comboBoxModel.getSelectionModel().clearSelection();
        comboBoxModel.setDisable(true);
    }

    @FXML
    void exitForm() {
        Stage stage = (Stage) labelVin.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveCar() {
        Model model = listModel.stream()
                .filter(searchModel -> Objects.equals(
                        searchModel.getModelName(),
                        comboBoxModel.getSelectionModel().getSelectedItem()))
                .findFirst()
                .orElseThrow(null);
        Car car = new Car(
                labelRegisterNumber.getText(),
                labelVin.getText(),
                Double.parseDouble(labelFuel.getText()),
                Integer.parseInt(labelSeries.getText()),
                Integer.parseInt(labelOdometer.getText()),
                model
        );
        carService.saveCarData(car);
        exitForm();
    }

    @FXML
    void afterClosingComboBox() {
        comboBoxModel.setDisable(comboBoxBrand.getSelectionModel().isEmpty());

        List<Model> sortListModel = listModel.stream().filter(model ->
                Objects.equals(model.getCarBrand().getBrandName(),
                        comboBoxBrand.getSelectionModel().getSelectedItem()
                )
        ).toList();

        comboBoxModel.setItems(getObservableList(modelService.mapModelListToStringList(sortListModel)));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listModel = modelService.getModelList();
        listBrand = brandService.getBrandList();

        comboBoxBrand.setItems(getObservableList(brandService.mapBrandListToStringList(listBrand)));

        comboBoxModel.setDisable(comboBoxBrand.getSelectionModel().isEmpty());
    }

    private ObservableList<String> getObservableList(List<String> stringList) {
        return FXCollections.observableArrayList(stringList);
    }
}
