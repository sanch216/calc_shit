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
import javafx.scene.image.ImageView;
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
    void initialize(URL location, ResourceBundle resources) {

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

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
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
            } catch (NumberFormatException e) { // Проверка на ошибку с парсингом
                alert.setHeaderText("Ошибка! Введите числа без букв!");
                alert.setContentText("Ошибка при парсинге числа: " + e.getMessage());
                alert.showAndWait();
                System.out.println("Ошибка при парсинге числа: " + e.getMessage());
                if(alert.getResult() == ButtonType.OK) {

                }
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

            Stage resultStage = new Stage();
            resultStage.setTitle("Taxulator");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("physTable.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(loader.load(), 515, 400);
                scene.getStylesheets().add(getClass().getResource("/org/example/calc_shit/stylePhysResult.css").toExternalForm());

            } catch (IOException e) {
                System.err.println("Не могу загрузить physTable.fxml" + e.getMessage());
                e.printStackTrace();
                return;
            }

            PhysTableController controller = loader.getController();
            controller.setData(data); // <- Сюда идёт  список с налогами

            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("logo2.png")));
            Stage physTable = new Stage();

            physTable.getIcons().add(icon);
            physTable.setTitle("Asshole");
            physTable.setScene(scene);
            physTable.setResizable(false);
            physTable.showAndWait();

        });
    }
}
