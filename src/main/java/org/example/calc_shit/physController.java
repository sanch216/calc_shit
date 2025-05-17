package org.example.calc_shit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

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

    private List<Data> currentData = new ArrayList<>();

    @FXML
    void initialize() {

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
            double totalValue = 0.0;

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
            } catch (NumberFormatException e) {
                alert.setHeaderText("Ошибка! Введите числа без букв!");
                alert.setContentText("Ошибка при парсинге числа: " + e.getMessage());
                alert.showAndWait();
                return;
            }

            Data mainJob = new Data("Основная работа", doubMainJobValue);
            Data extraJob = new Data("Дополнительная работа", doubExtraJobValue);
            Data propertySale = new Data("Продажа имущества", doubPropertySaleValue);
            Data transfer = new Data("Зарубежные переводы", doubTransferValue);
            Data total = new Data("Всего", totalValue);

            List<Data> data = new ArrayList<>();
            data.add(mainJob);
            data.add(extraJob);
            data.add(propertySale);
            data.add(transfer);
            data.add(total);

            double res = 0;
            if (benefit.isSelected()) {
                for (Data item : data) {
                    if (item.getName().equals("Основная работа")) {
                        item.setTax(item.getValue() * benefitValue);
                    } else if (!item.getName().equals("Всего")) {
                        item.setTax(item.getValue() * generalValue);
                    }
                    if (!item.getName().equals("Всего")) {
                        totalValue = item.sumTotal(item.getTax());
                        res += totalValue;
                    }
                }
            } else {
                for (Data item : data) {
                    if (!item.getName().equals("Всего")) {
                        item.setTax(item.getValue() * generalValue);
                        totalValue = item.sumTotal(item.getTax());
                        res += totalValue;
                    }
                }
            }
            total.setTotal(res);
            currentData = data;

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("physTable.fxml"));
                Scene scene = new Scene(loader.load(), 515, 400);
                scene.getStylesheets().add(getClass().getResource("/org/example/calc_shit/stylePhysResult.css").toExternalForm());

                PhysTableController controller = loader.getController();
                controller.setData(data);

                Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("logo2.png")));
                Stage physTable = new Stage();
                physTable.getIcons().add(icon);
                physTable.setTitle("Asshole");
                physTable.setScene(scene);
                physTable.setResizable(false);
                physTable.showAndWait();

            } catch (IOException e) {
                System.err.println("Не могу загрузить physTable.fxml: " + e.getMessage());
                e.printStackTrace();
            }
        });

        toExcel.setOnAction(e -> exportToExcel(currentData));
    }

    private void exportToExcel(List<Data> data) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Налоги");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Источник дохода");
        header.createCell(1).setCellValue("Сумма дохода");
        header.createCell(2).setCellValue("Сумма налога");

        int rowNum = 1;
        for (Data item : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item.getName());
            row.createCell(1).setCellValue(item.getValue());
            row.createCell(2).setCellValue(item.getTax());
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить как Excel");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel файлы", "*.xlsx"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
                workbook.close();

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Успешно");
                success.setHeaderText(null);
                success.setContentText("Файл сохранён: " + file.getAbsolutePath());
                success.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Ошибка");
                error.setHeaderText("Не удалось сохранить файл");
                error.setContentText(e.getMessage());
                error.showAndWait();
            }
        }
    }
}
