package ru.fsv67.controllers.car;

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
import ru.fsv67.controllers.MainScreenController;
import ru.fsv67.models.car.ModelView;
import ru.fsv67.services.car.ModelService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@EnableConfigurationProperties(AppProperties.class)
@FxmlView("/screens/car/model-list.fxml")
@RequiredArgsConstructor
public class ModelListScreenController implements Initializable {
    private final FxWeaver fxWeaver;
    private final AppProperties titleApp;
    private final ModelService modelService;

    @FXML
    private JFXButton buttonDelete;

    @FXML
    private JFXButton buttonUpdate;

    @FXML
    private JFXTreeTableView<ModelView> tableView;

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
        Scene scene = new Scene(fxWeaver.loadView(ModelCreateScreenController.class));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.showAndWait();
        entreDataIntoTable();
    }

    @FXML
    void onClickDelete() {
        Long modelId = tableView.getSelectionModel().getSelectedItem().getValue().getId().getValue();
        modelService.deleteModelData(modelId);
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
            fxWeaver.loadController(ModelUpdateScreenController.class).modelView =
                    tableView.getSelectionModel().getSelectedItem().getValue();
        }
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(ModelUpdateScreenController.class));
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

        JFXTreeTableColumn<ModelView, String> numberLine = new JFXTreeTableColumn<>("№ п.п.");
        numberLine.setMinWidth(90);
        numberLine.setCellValueFactory(param -> param.getValue().getValue().getNumberLine().asString());
        numberLine.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<ModelView, String> modelName = new JFXTreeTableColumn<>("Название модели");
        modelName.setMinWidth(200);
        modelName.setCellValueFactory(param -> param.getValue().getValue().getModelName());
        modelName.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<ModelView, String> brandName = new JFXTreeTableColumn<>("Название бренда");
        brandName.setMinWidth(200);
        brandName.isReorderable();
        brandName.setCellValueFactory(param -> param.getValue().getValue().getNameBrand());
        brandName.setStyle("-fx-alignment: center;");

        tableView.getColumns().setAll(List.of(numberLine, modelName, brandName));

        entreDataIntoTable();
    }

    void entreDataIntoTable() {
        final TreeItem<ModelView> modelTreeItem =
                new RecursiveTreeItem<>(
                        FXCollections.observableArrayList(
                                modelService.mapToModelViewList(modelService.getModelList())
                        ),
                        RecursiveTreeObject::getChildren
                );
        tableView.setRoot(modelTreeItem);
        tableView.setShowRoot(false);
    }
}
