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
import ru.fsv67.services.driver.DriverLicenseService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
@FxmlView("/screens/driver/driver-license-create.fxml")
public class DriverLicenseCreateScreenController implements Initializable {
    private final DriverLicenseService driverLicenseService;
    Long driverId;
    @FXML
    private Group categories;
    
    @FXML
    private JFXComboBox<String> comboReally;

    @FXML
    private DatePicker issueDate;

    @FXML
    private DatePicker validityDate;

    @FXML
    private JFXTextField numberVUField;

    @FXML
    private JFXTextField seriesVUField;

    @FXML
    void onClickClear() {
        for (Node node : categories.getChildren()) {
            JFXCheckBox checkBox = (JFXCheckBox) node;
            checkBox.setSelected(false);
        }
        seriesVUField.setText("");
        numberVUField.setText("");
        comboReally.getSelectionModel().clearSelection();
        issueDate.getEditor().clear();
        validityDate.getEditor().clear();
    }

    @FXML
    void onClickCreate() {
        if (comboReally.getSelectionModel().getSelectedItem() != null &&
                issueDate.getValue() != null && validityDate.getValue() != null
                && !seriesVUField.getText().isEmpty() && !numberVUField.getText().isEmpty() &&
                !getCategories().isEmpty()) {
            driverLicenseService.saveDriverLicense(new DriversLicense(
                    seriesVUField.getText() + " " + numberVUField.getText(),
                    getCategories(),
                    issueDate.getValue(),
                    validityDate.getValue(),
                    comboReally.getSelectionModel().getSelectedItem().equals("Действительно"),
                    driverId
            ));
        }
        onClickExit();
    }

    @FXML
    void onClickExit() {
        Stage stage = (Stage) numberVUField.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboReally.setItems(getObservableList(List.of("Действительно", "Не действительно")));
    }

    private ObservableList<String> getObservableList(List<String> stringList) {
        return FXCollections.observableArrayList(stringList);
    }

    /**
     * Формируем строку из категорий
     *
     * @return Строки из категорий
     */
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
}
