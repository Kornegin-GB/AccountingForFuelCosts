package ru.fsv67.controllers.car;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.fsv67.models.car.Brand;
import ru.fsv67.models.car.BrandView;
import ru.fsv67.services.car.BrandService;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("/screens/car/brand-update.fxml")
@RequiredArgsConstructor
public class BrandUpdateScreenController implements Initializable {
    private final BrandService brandService;
    BrandView brandView;

    @FXML
    private JFXTextField labelBrandName;

    @FXML
    void exitForm() {
        brandView = null;
        Stage stage = (Stage) labelBrandName.getScene().getWindow();
        stage.close();
    }

    @FXML
    void updateBrand() {
        Brand brand = new Brand(
                brandView.getId().longValue(),
                labelBrandName.getText()
        );
        brandService.updateBrandData(brand);
        exitForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (brandView != null) {
            labelBrandName.setText(brandView.getBrandName().getValue());
        }
    }
}
