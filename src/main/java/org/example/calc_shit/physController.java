package org.example.calc_shit;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class physController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox benefit;

    @FXML
    private Button calculate;

    @FXML
    private Text extraJob;

    @FXML
    private TextField extraJobField;

    @FXML
    private Text income;

    @FXML
    private Text mainJob;

    @FXML
    private TextField mainJobField;

    @FXML
    private Text propertySale;

    @FXML
    private TextField propertySaleField;

    @FXML
    private Button toExcel;

    @FXML
    private Text transfer;

    @FXML
    private TextField transferField;

    @FXML
    void initialize() {
        assert benefit != null : "fx:id=\"benefit\" was not injected: check your FXML file 'phys.fxml'.";
        assert calculate != null : "fx:id=\"calculate\" was not injected: check your FXML file 'phys.fxml'.";
        assert extraJob != null : "fx:id=\"extraJob\" was not injected: check your FXML file 'phys.fxml'.";
        assert extraJobField != null : "fx:id=\"extraJobField\" was not injected: check your FXML file 'phys.fxml'.";
        assert income != null : "fx:id=\"income\" was not injected: check your FXML file 'phys.fxml'.";
        assert mainJob != null : "fx:id=\"mainJob\" was not injected: check your FXML file 'phys.fxml'.";
        assert mainJobField != null : "fx:id=\"mainJobField\" was not injected: check your FXML file 'phys.fxml'.";
        assert propertySale != null : "fx:id=\"propertySale\" was not injected: check your FXML file 'phys.fxml'.";
        assert propertySaleField != null : "fx:id=\"propertySaleField\" was not injected: check your FXML file 'phys.fxml'.";
        assert toExcel != null : "fx:id=\"toExcel\" was not injected: check your FXML file 'phys.fxml'.";
        assert transfer != null : "fx:id=\"transfer\" was not injected: check your FXML file 'phys.fxml'.";
        assert transferField != null : "fx:id=\"transferField\" was not injected: check your FXML file 'phys.fxml'.";

        Font font18 = Font.loadFont(getClass().getResourceAsStream("/fonts/DimboRussian.otf"), 18);
        Font font20 = Font.loadFont(getClass().getResourceAsStream("/fonts/DimboRussian.otf"), 18);
        Font font26 = Font.loadFont(getClass().getResourceAsStream("/fonts/DimboRussian.otf"), 18);
        income.setFont(font26);
        extraJob.setFont(font20);
        transfer.setFont(font20);
        propertySale.setFont(font20);
        mainJob.setFont(font20);
        benefit.setFont(font18);
        toExcel.setFont(font18);
        calculate.setFont(font18);

//        calculate.setOnAction(event ->
//        {
//
//            double benefitValue = 0.05;
//            double generalValue = 0.1;
//
//            String mainJobValue = mainJobField.getText();
//            String extraJobValue = extraJobField.getText();
//            String propertySaleValue = propertySaleField.getText();
//            String transferValue = transferField.getText();
//
//
//
//
//            double doubMainJobValue = 0.0;
//            double doubExtraJobValue = 0.0;
//            double doubPropertySaleValue = 0.0;
//            double doubTransferValue = 0.0;
//
//            try {
//                if (mainJobValue != null && !mainJobValue.isEmpty()) {
//                    doubMainJobValue = Double.parseDouble(mainJobValue);
//                }
//                if (extraJobValue != null && !extraJobValue.isEmpty()) {
//                    doubExtraJobValue = Double.parseDouble(extraJobValue);
//                }
//                if (propertySaleValue != null && !propertySaleValue.isEmpty()) {
//                    doubPropertySaleValue = Double.parseDouble(propertySaleValue);
//                }
//                if (transferValue != null && !transferValue.isEmpty()) {
//                    doubTransferValue = Double.parseDouble(transferValue);
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Ошибка при парсинге числа: " + e.getMessage());
//            }
//            Data mainJob = new Data("Основная работа",doubMainJobValue);
//            Data extraJob = new Data("Дополнительная работа",doubExtraJobValue);
//            Data propertySale = new Data("Продажа имущества",doubPropertySaleValue);
//            Data transfer = new Data("Зарубежные переводы",doubTransferValue);
//
//            List<Data> data = new ArrayList<>();
//            data.add(mainJob);
//            data.add(extraJob);
//            data.add(propertySale);
//            data.add(transfer);
//            data.sort((a,b) -> Double.compare(a.value, b.value));
//
//            for (Data item : data) {
//                System.out.println(item.name + ": " + item.value);
//            }
//
//
//            double resultray[] = {doubExtraJobValue,doubPropertySaleValue,doubTransferValue,doubMainJobValue};
//            double result[] = {0,0,0,0};
//
//            if(benefit.isSelected()){
//                for (int i = 0; i < 3; i++) {
//                    resultray[i] = resultray[i] * generalValue;
//                    result[i] = resultray[i];
//                }
//                result[3] = doubMainJobValue * benefitValue;
//                System.out.println(Arrays.toString(result));
//            }else {
//
//                    for (int i = 0; i < 4; i++) {
//                        resultray[i] = resultray[i] * generalValue;
//                        result[i] = resultray[i];
//                    }
//                    System.out.println(Arrays.toString(result));
//                }
//
//        });
//
//
//    }
        calculate.setOnAction(event -> {
            double benefitValue = 0.05;
            double generalValue = 0.1;

            String mainJobValue = mainJobField.getText();
            String extraJobValue = extraJobField.getText();
            String propertySaleValue = propertySaleField.getText();
            String transferValue = transferField.getText();

            double doubMainJobValue = 0.0;
            double doubExtraJobValue = 0.0;
            double doubPropertySaleValue = 0.0;
            double doubTransferValue = 0.0;

            try {
                if (mainJobValue != null && !mainJobValue.isEmpty()) {
                    doubMainJobValue = Double.parseDouble(mainJobValue);
                }
                if (extraJobValue != null && !extraJobValue.isEmpty()) {
                    doubExtraJobValue = Double.parseDouble(extraJobValue);
                }
                if (propertySaleValue != null && !propertySaleValue.isEmpty()) {
                    doubPropertySaleValue = Double.parseDouble(propertySaleValue);
                }
                if (transferValue != null && !transferValue.isEmpty()) {
                    doubTransferValue = Double.parseDouble(transferValue);
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка при парсинге числа: " + e.getMessage());
            }

            // Создаем объекты Data
            Data mainJob = new Data("Основная работа", doubMainJobValue);
            Data extraJob = new Data("Дополнительная работа", doubExtraJobValue);
            Data propertySale = new Data("Продажа имущества", doubPropertySaleValue);
            Data transfer = new Data("Зарубежные переводы", doubTransferValue);

            List<Data> data = new ArrayList<>();
            data.add(mainJob);
            data.add(extraJob);
            data.add(propertySale);
            data.add(transfer);

            // Расчет налогов
            if (benefit.isSelected()) {
                for (Data item : data) {
                    if (item.getName().equals("Основная работа")) {
                        item.setTax(item.getValue() * benefitValue);
                    } else {
                        item.setTax(item.getValue() * generalValue);
                    }
                }
            } else {
                for (Data item : data) {
                    item.setTax(item.getValue() * generalValue);
                }
            }

//            // Создаем новое окно
            Stage resultStage = new Stage();
            resultStage.setTitle("Налоговый расчет");

            // Создаем TableView для вывода
//            TableView<Data> table = new TableView<>();
//            TableColumn<Data, String> nameColumn = new TableColumn<>("Источник дохода");
//            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//            TableColumn<Data, Double> valueColumn = new TableColumn<>("Доход");
//            valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
//            TableColumn<Data, Double> taxColumn = new TableColumn<>("Налог");
//            taxColumn.setCellValueFactory(new PropertyValueFactory<>("tax"));
//
//            table.getColumns().addAll(nameColumn, valueColumn, taxColumn);
//            table.getItems().addAll(data);
//
//            // Настраиваем размеры колонок
//            nameColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.4));
//            valueColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
//            taxColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
//
//            // Создаем сцену и показываем окно
//            VBox vbox = new VBox(table);
//            Scene scene = new Scene(vbox, 600, 400);
//            //scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylePhysResult.css")).toExternalForm());
//            resultStage.setScene(scene);
//            resultStage.show();



            FXMLLoader loader = new FXMLLoader(getClass().getResource("physTable.fxml"));

            Scene scene = null;
            try {
                scene = new Scene(loader.load(), 515, 400);
                scene.getStylesheets().add(getClass().getResource("/org/example/calc_shit/stylePhysResult.css").toExternalForm());

            } catch (IOException e) {
                System.err.println("Не могу загрузить physTable.fxml" + e.getMessage());
                e.printStackTrace();
                return; // или покажи Alert с ошибкой
            }
            PhysTableController controller = loader.getController();
            controller.setData(data); // <- Сюда идёт твой список с налогами

            Stage physTable = new Stage();
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("logo2.png")));
            physTable.getIcons().add(icon);
            physTable.setTitle("Asshole");
            physTable.setScene(scene);
            physTable.setResizable(false);
            physTable.showAndWait();


        });
    }
}
