package org.example.calc_shit;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;


public class PhysTableController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Data> physTable;

    @FXML
    private TableColumn<Data, Double> value; // источник Значение

    @FXML
    private TableColumn<Data, Double> tax; // сам налог

    @FXML
    private TableColumn<Data, String> nameIncome; // название источника

    @FXML
    void initialize() {

        value.setCellValueFactory(new PropertyValueFactory<> ("value"));
        tax.setCellValueFactory(new PropertyValueFactory<>("tax"));
        nameIncome.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
    public void setData(List<Data> dataList){
        physTable.getItems().addAll(dataList);
    }
}
