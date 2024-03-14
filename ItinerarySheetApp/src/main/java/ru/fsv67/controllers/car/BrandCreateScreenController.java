package ru.fsv67.controllers.car;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.fsv67.models.car.Brand;
import ru.fsv67.services.car.BrandService;

@Component
@FxmlView("/screens/car/brand-create.fxml")
@RequiredArgsConstructor
public class BrandCreateScreenController {
    private final BrandService brandService;
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
        Brand brand = new Brand(
                labelBrandName.getText()
        );
        brandService.saveBrandData(brand);
        exitForm();
    }
}
