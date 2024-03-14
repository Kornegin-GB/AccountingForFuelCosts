package ru.fsv67.controllers.driver;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.fsv67.models.driver.Driver;
import ru.fsv67.services.driver.DriverService;

@Component
@RequiredArgsConstructor
@FxmlView("/screens/driver/driver-create.fxml")
public class DriverCreateScreenController {
    private final DriverService driverService;
    @FXML
    private JFXTextField firstNameField;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField parentNameField;

    @FXML
    private JFXTextField snilsField;

    @FXML
    void onClickClear() {
        firstNameField.setText("");
        lastNameField.setText("");
        parentNameField.setText("");
        snilsField.setText("");
    }

    @FXML
    void onClickCreate() {
        Driver driver = new Driver(
                firstNameField.getText(),
                lastNameField.getText(),
                parentNameField.getText(),
                snilsField.getText()
        );
        driverService.saveDriver(driver);
        onClickExit();
    }

    @FXML
    void onClickExit() {
        Stage stage = (Stage) firstNameField.getScene().getWindow();
        stage.close();
    }
}
