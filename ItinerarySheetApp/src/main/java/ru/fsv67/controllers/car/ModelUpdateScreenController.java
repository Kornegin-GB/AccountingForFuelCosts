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
import ru.fsv67.models.car.Model;
import ru.fsv67.models.car.ModelView;
import ru.fsv67.services.car.BrandService;
import ru.fsv67.services.car.ModelService;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@FxmlView("/screens/car/model-update.fxml")
@RequiredArgsConstructor
public class ModelUpdateScreenController implements Initializable {
    private final BrandService brandService;
    private final ModelService modelService;
    ModelView modelView;
    List<Brand> brandList;

    @FXML
    private JFXTextField labelModel;

    @FXML
    private JFXComboBox<String> comboBoxBrand;

    @FXML
    void exitForm() {
        modelView = null;
        Stage stage = (Stage) labelModel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void updateModel() {
        Brand brand = brandList.stream()
                .filter(searchBrand -> Objects.equals(
                                searchBrand.getBrandName(), comboBoxBrand.getSelectionModel().getSelectedItem()
                        )
                ).findFirst().orElseThrow(null);
        Model model = new Model(
                modelView.getId().longValue(),
                labelModel.getText(),
                brand
        );
        modelService.updateModelData(model);
        exitForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        brandList = brandService.getBrandList();
        comboBoxBrand.setItems(getObservableList(brandService.mapBrandListToStringList(brandList)));

        if (modelView != null) {
            labelModel.setText(modelView.getModelName().getValue());
            comboBoxBrand.getSelectionModel().select(modelView.getNameBrand().getValue());
        }
    }

    private ObservableList<String> getObservableList(List<String> stringList) {
        return FXCollections.observableArrayList(stringList);
    }
}
