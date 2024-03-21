package ru.fsv67.controllers.itinerarySheet;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.fsv67.AppProperties;
import ru.fsv67.controllers.ErrorScreenController;
import ru.fsv67.controllers.MainScreenController;
import ru.fsv67.models.itinerarySheet.FuelBrandView;
import ru.fsv67.services.itinerarySheet.FuelBrandService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@EnableConfigurationProperties(AppProperties.class)
@FxmlView("/screens/itinerarySheet/fuel-brand-list.fxml")
@RequiredArgsConstructor
public class FuelBrandListScreenController implements Initializable {
    private final FxWeaver fxWeaver;
    private final AppProperties titleApp;
    private final FuelBrandService fuelBrandService;

    @FXML
    private JFXButton buttonDelete;

    @FXML
    private JFXButton buttonUpdate;

    @FXML
    private JFXTreeTableView<FuelBrandView> tableView;

    @FXML
    void onSelectingLine() {
        isDisableButton();
    }

    void isDisableButton() {
        buttonUpdate.setDisable(tableView.getSelectionModel().getSelectedIndex() < 0);
        buttonDelete.setDisable(tableView.getSelectionModel().getSelectedIndex() < 0);
    }

    @FXML
    void onClickCreate() {
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(FuelBrandCreateScreenController.class));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.showAndWait();
        entreDataIntoTable();
    }

    @FXML
    void onClickDelete() {
        Long fuelBrandId = tableView.getSelectionModel().getSelectedItem().getValue().getId().getValue();
        try {
            fuelBrandService.deleteFuelBrand(fuelBrandId);
        } catch (RuntimeException e) {
            fxWeaver.loadController(ErrorScreenController.class).message = e.getMessage();
            Stage stage = new Stage();
            Scene scene = new Scene(fxWeaver.loadView(ErrorScreenController.class));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.showAndWait();
        }
        entreDataIntoTable();
        isDisableButton();
    }

    @FXML
    void onClickExit() {
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(MainScreenController.class));
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.setResizable(false);
        stage.show();
        Stage windowStage = (Stage) buttonDelete.getScene().getWindow();
        windowStage.close();
    }

    @FXML
    void onClickUpdate() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            fxWeaver.loadController(FuelBrandUpdateScreenController.class).fuelBrandView =
                    tableView.getSelectionModel().getSelectedItem().getValue();
        }
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(FuelBrandUpdateScreenController.class));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.showAndWait();
        entreDataIntoTable();
        isDisableButton();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        isDisableButton();

        JFXTreeTableColumn<FuelBrandView, String> numberLine = new JFXTreeTableColumn<>("№ п.п.");
        numberLine.setMinWidth(100);
        numberLine.setCellValueFactory(param -> param.getValue().getValue().getNumberLine().asString());
        numberLine.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<FuelBrandView, String> fuelBrandName = new JFXTreeTableColumn<>("Название бренда топлива");
        fuelBrandName.setMinWidth(300);
        fuelBrandName.setCellValueFactory(param -> param.getValue().getValue().getFuelBrandName());
        fuelBrandName.setStyle("-fx-alignment: center;");

        tableView.getColumns().setAll(List.of(numberLine, fuelBrandName));

        entreDataIntoTable();
    }

    void entreDataIntoTable() {
        final TreeItem<FuelBrandView> fuelBrandViewTreeItem =
                new RecursiveTreeItem<>(
                        FXCollections.observableArrayList(
                                fuelBrandService.mapToFuelBrandViewList(fuelBrandService.getFuelBrandList())
                        ),
                        RecursiveTreeObject::getChildren
                );
        tableView.setRoot(fuelBrandViewTreeItem);
        tableView.setShowRoot(false);
    }
}
