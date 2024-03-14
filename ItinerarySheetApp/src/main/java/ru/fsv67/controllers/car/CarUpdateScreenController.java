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
import ru.fsv67.models.car.CarView;
import ru.fsv67.models.car.Model;
import ru.fsv67.services.car.BrandService;
import ru.fsv67.services.car.CarService;
import ru.fsv67.services.car.ModelService;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@FxmlView("/screens/car/car-update.fxml")
@RequiredArgsConstructor
public class CarUpdateScreenController implements Initializable {
    private final BrandService brandService;
    private final ModelService modelService;
    private final CarService carService;
    CarView carView;
    List<Brand> listBrand;
    List<Model> modelList;
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
    void afterClosingComboBox() {
        sortedModelList();
    }

    @FXML
    void beforeShowingComboBox() {
        sortedModelList();
    }

    void sortedModelList() {
        List<Model> sortModelList = modelList.stream().filter(model ->
                Objects.equals(model.getCarBrand().getBrandName(),
                        comboBoxBrand.getSelectionModel().getSelectedItem()
                )
        ).toList();

        comboBoxModel.setItems(getObservableList(modelService.mapModelListToStringList(sortModelList)));
    }


    @FXML
    void exitForm() {
        carView = null;
        Stage stage = (Stage) labelVin.getScene().getWindow();
        stage.close();
    }

    @FXML
    void updateCar() {
        Model model = modelList.stream()
                .filter(searchModel -> Objects.equals(
                        searchModel.getModelName(),
                        comboBoxModel.getSelectionModel().getSelectedItem()))
                .findFirst()
                .orElseThrow(null);
        Car car = new Car(
                carView.getId().longValue(),
                labelRegisterNumber.getText(),
                labelVin.getText(),
                Double.parseDouble(labelFuel.getText()),
                Integer.parseInt(labelSeries.getText()),
                Integer.parseInt(labelOdometer.getText()),
                model
        );
        carService.updateCarData(car);
        exitForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listBrand = brandService.getBrandList();
        modelList = modelService.getModelList();

        comboBoxBrand.setItems(getObservableList(brandService.mapBrandListToStringList(listBrand)));
        comboBoxModel.setItems(getObservableList(modelService.mapModelListToStringList(modelList)));

        if (carView != null) {
            comboBoxBrand.getSelectionModel().select(carView.getCarBrand().getValue());
            labelVin.setText(carView.getVin().getValue());
            labelFuel.setText(String.valueOf(carView.getFuelConsumptionRate().getValue()));
            labelRegisterNumber.setText(String.valueOf(carView.getRegistrationNumber().getValue()));
            labelSeries.setText(String.valueOf(carView.getSeriesItinerarySheet().getValue()));
            labelOdometer.setText(String.valueOf(carView.getInitialOdometer().getValue()));
            comboBoxModel.getSelectionModel().select(carView.getCarModel().getValue());
        }
    }

    private ObservableList<String> getObservableList(List<String> stringList) {
        return FXCollections.observableArrayList(stringList);
    }
}
