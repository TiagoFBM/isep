package lapr.project.spapp.ui.javafxinterface.uc18;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lapr.project.spapp.controller.CheckTimelineController;
import lapr.project.spapp.model.ExecutionOrder;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckTimeLineWindowController implements Initializable {

    public ComboBox cmbClient;
    public ListView lstViewEO;

    private Stage previousWindowStage;
    private Stage thisWindowStage;
    private CheckTimelineController oController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        oController = new CheckTimelineController();
        updateComboBox();
    }

    public void selectClientAction(ActionEvent actionEvent) {
        String selected = (String) cmbClient.getSelectionModel().getSelectedItem();

        ObservableList<ExecutionOrder> lst = FXCollections.observableArrayList(oController.sortTimeLine(selected));
        lstViewEO.setItems(lst);
    }

    public void cancelAction(ActionEvent actionEvent) {
        previousWindowStage.show();
        thisWindowStage.close();
    }

    public void setPreviousWindowStage(Stage stage) {
        previousWindowStage = stage;
    }

    public void setThisWindowStage(Stage stage) {
        thisWindowStage = stage;
    }

    private void updateComboBox() {
        ObservableList<String> lstCMB = FXCollections.observableArrayList(oController.getClientList());
        cmbClient.setItems(lstCMB);
    }

    public void setOnClose() {
        thisWindowStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                previousWindowStage.show();
                thisWindowStage.close();
            }
        });
    }

    public boolean validatesEOSize(){
        return oController.validatesEOSize();
    }

}
