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
import ru.fsv67.models.car.BrandView;
import ru.fsv67.services.car.BrandService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@EnableConfigurationProperties(AppProperties.class)
@FxmlView("/screens/car/brand-list.fxml")
@RequiredArgsConstructor
public class BrandListScreenController implements Initializable {
    private final FxWeaver fxWeaver;
    private final AppProperties titleApp;
    private final BrandService brandService;

    @FXML
    private JFXButton buttonDelete;

    @FXML
    private JFXButton buttonUpdate;

    @FXML
    private JFXTreeTableView<BrandView> tableView;

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
        Scene scene = new Scene(fxWeaver.loadView(BrandCreateScreenController.class));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.showAndWait();
        entreDataIntoTable();
    }

    @FXML
    void onClickDelete() {
        Long brandId = tableView.getSelectionModel().getSelectedItem().getValue().getId().getValue();
        brandService.deleteBrandData(brandId);
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
            fxWeaver.loadController(BrandUpdateScreenController.class).brandView =
                    tableView.getSelectionModel().getSelectedItem().getValue();
        }
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(BrandUpdateScreenController.class));
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

        JFXTreeTableColumn<BrandView, String> numberLine = new JFXTreeTableColumn<>("№ п.п.");
        numberLine.setMinWidth(100);
        numberLine.setCellValueFactory(param -> param.getValue().getValue().getNumberLine().asString());
        numberLine.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<BrandView, String> brandName = new JFXTreeTableColumn<>("Название бренда");
        brandName.setMinWidth(300);
        brandName.setCellValueFactory(param -> param.getValue().getValue().getBrandName());
        brandName.setStyle("-fx-alignment: center;");

        tableView.getColumns().setAll(List.of(numberLine, brandName));

        entreDataIntoTable();
    }

    void entreDataIntoTable() {
        final TreeItem<BrandView> brandTreeItem =
                new RecursiveTreeItem<>(
                        FXCollections.observableArrayList(
                                brandService.mapToBrandViewList(brandService.getBrandList())
                        ),
                        RecursiveTreeObject::getChildren
                );
        tableView.setRoot(brandTreeItem);
        tableView.setShowRoot(false);
    }
}
