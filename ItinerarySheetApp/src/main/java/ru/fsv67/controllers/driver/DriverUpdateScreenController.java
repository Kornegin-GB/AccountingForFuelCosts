package ru.fsv67.controllers.driver;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.fsv67.models.driver.Driver;
import ru.fsv67.services.driver.DriverService;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
@FxmlView("/screens/driver/driver-update.fxml")
public class DriverUpdateScreenController implements Initializable {
    private final DriverService driverService;
    Driver driver;

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField parentNameField;

    @FXML
    private JFXTextField snilsField;

    @FXML
    void onClickExit() {
        Stage stage = (Stage) firstNameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickUpdate() {
        Driver updateDriver = new Driver(
                driver.getId(),
                firstNameField.getText(),
                lastNameField.getText(),
                parentNameField.getText(),
                snilsField.getText(),
                driver.getDriversLicense()
        );
        driverService.updateDriver(updateDriver);
        onClickExit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (driver != null) {
            firstNameField.setText(driver.getFirstName());
            lastNameField.setText(driver.getLastName());
            parentNameField.setText(driver.getParentName());
            snilsField.setText(driver.getSnils());
        }
    }
}
