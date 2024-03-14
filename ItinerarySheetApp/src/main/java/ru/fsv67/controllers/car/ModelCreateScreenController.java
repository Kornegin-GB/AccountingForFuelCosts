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
import ru.fsv67.services.car.BrandService;
import ru.fsv67.services.car.ModelService;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@FxmlView("/screens/car/model-create.fxml")
@RequiredArgsConstructor
public class ModelCreateScreenController implements Initializable {
    private final ModelService modelService;
    private final BrandService brandService;
    List<Brand> brandList;
    @FXML
    private JFXComboBox<String> comboBoxBrand;

    @FXML
    private JFXTextField labelModel;


    @FXML
    void clearForm() {
        labelModel.setText("");
        comboBoxBrand.getSelectionModel().clearSelection();
    }

    @FXML
    void exitForm() {
        Stage stage = (Stage) labelModel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveModel() {
        Brand brand = brandList.stream()
                .filter(searchBrand -> Objects.equals(
                                searchBrand.getBrandName(), comboBoxBrand.getSelectionModel().getSelectedItem()
                        )
                ).findFirst().orElseThrow(null);
        Model model = new Model(
                labelModel.getText(),
                brand
        );
        modelService.saveModelData(model);
        exitForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        brandList = brandService.getBrandList();
        comboBoxBrand.setItems(getObservableList(brandService.mapBrandListToStringList(brandList)));
    }

    private ObservableList<String> getObservableList(List<String> stringList) {
        return FXCollections.observableArrayList(stringList);
    }
}
