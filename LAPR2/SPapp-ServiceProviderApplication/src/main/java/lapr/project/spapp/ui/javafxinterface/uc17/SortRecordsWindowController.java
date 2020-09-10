/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.spapp.ui.javafxinterface.uc17;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lapr.project.spapp.controller.SortRecordsController;
import lapr.project.spapp.model.ExecutionOrder;

/**
 * FXML Controller class
 *
 * @author anama
 */
public class SortRecordsWindowController implements Initializable {

    private SortRecordsController m_oController;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private ListView<ExecutionOrder> listView;

    private Stage previousWindowStage;
    private Stage thisWindowStage;
    private List<ExecutionOrder> leo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        m_oController = new SortRecordsController();
        comboBox.getItems().addAll("Assignment Date", "Service", "Client", "Address");
        setLstViewExecutionOrders();
    }

    public void setPreviousWindowStage(Stage stage) {
        previousWindowStage = stage;
    }

    public void setThisWindowStage(Stage stage) {
        thisWindowStage = stage;
    }

    public void setOnClose() {
        thisWindowStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                previousWindowStage.show();
                thisWindowStage.close();
            }
        });
    }

    @FXML
    private void actionFilterBy(ActionEvent event) {
        String selected = this.comboBox.getSelectionModel().getSelectedItem();
        m_oController.getComparationByString(selected);
        leo = m_oController.sortRecords();
        setLstViewExecutionOrders();
    }

    private void setLstViewExecutionOrders() {
        ObservableList<ExecutionOrder> lst = FXCollections.observableArrayList(m_oController.getListEO());
        listView.setItems(lst);
    }

    @FXML
    private void actionCancel(ActionEvent event) {
        previousWindowStage.show();
        thisWindowStage.close();
    }

    public boolean validatesEOSize() {
        return m_oController.validatesEOSize();
    }
}
