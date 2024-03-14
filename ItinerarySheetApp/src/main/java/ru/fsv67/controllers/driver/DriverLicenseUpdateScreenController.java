package ru.fsv67.controllers.driver;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.fsv67.models.driver.DriversLicense;
import ru.fsv67.models.driver.DriversLicenseView;
import ru.fsv67.services.driver.DriverLicenseService;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
@FxmlView("/screens/driver/driver-license-update.fxml")
public class DriverLicenseUpdateScreenController implements Initializable {
    private final DriverLicenseService driverLicenseService;
    DriversLicenseView driversLicenseView;
    @FXML
    private Group categories;

    @FXML
    private JFXComboBox<String> comboReally;

    @FXML
    private DatePicker issueDate;

    @FXML
    private JFXTextField numberVUField;

    @FXML
    private JFXTextField seriesVUField;

    @FXML
    private DatePicker validityDate;

    @FXML
    void onClickExit() {
        Stage stage = (Stage) numberVUField.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickUpdate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        driverLicenseService.updateDriverLicense(
                new DriversLicense(
                        driversLicenseView.getId().getValue(),
                        seriesVUField.getText() + " " + numberVUField.getText(),
                        getCategories(),
                        LocalDate.parse(issueDate.getEditor().getText(), formatter),
                        LocalDate.parse(validityDate.getEditor().getText(), formatter),
                        comboReally.getSelectionModel().getSelectedItem().equals("Действительно"),
                        driversLicenseView.getDriverId().getValue()
                )
        );
        onClickExit();
    }

    private void getCheckedCategories() {
        String[] stringList = driversLicenseView.getCategories().getValue().split(", ");
        for (Node node : categories.getChildren()) {
            JFXCheckBox checkBox = (JFXCheckBox) node;
            if (Arrays.asList(stringList).contains(checkBox.getText())) {
                checkBox.setSelected(true);
            }
        }
    }

    private void getNumberVU() {
        String[] stringList = driversLicenseView.getNumberDriversLicense().getValue().split(" ");
        seriesVUField.setText(stringList[0]);
        numberVUField.setText(stringList[1]);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboReally.setItems(getObservableList(List.of("Действительно", "Не действительно")));
        if (driversLicenseView != null) {
            getCheckedCategories();
            getNumberVU();
            issueDate.getEditor().setText(driversLicenseView.getDateOfIssue().getValue());
            validityDate.getEditor().setText(driversLicenseView.getDateOfValidity().getValue());
            comboReally.getSelectionModel().select(driversLicenseView.getIsReally().getValue());
        }
    }

    private String getCategories() {
        StringBuilder categoriesString = new StringBuilder();
        for (Node node : categories.getChildren()) {
            JFXCheckBox checkBox = (JFXCheckBox) node;
            if (checkBox.isSelected()) {
                categoriesString.append(checkBox.getText());
                categoriesString.append(", ");
            }
        }
        if (!categoriesString.isEmpty()) {
            categoriesString.setLength(categoriesString.length() - 2);
        }
        return categoriesString.toString();
    }

    private ObservableList<String> getObservableList(List<String> stringList) {
        return FXCollections.observableArrayList(stringList);
    }
}
