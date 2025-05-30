package org.example.calc_shit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.opc.PackageRelationship;
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


    @FXML
    private Text gift;

    @FXML
    private TextField giftField;

    @FXML
    private Button help;

    @FXML
    private Text royaltie;

    @FXML
    private TextField royaltieField;


    private List<Data> currentData = new ArrayList<>();

    @FXML
    void initialize() {

        help.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("help.fxml")));
                Stage stage = new Stage();
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Taxulator Help");
                String css = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();
                scene.getStylesheets().add(css);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        calculate.setOnAction(event -> {
            double benefitValue = 0.05;
            double generalValue = 0.1;

            String mainJobValue = mainJobField.getText();
            String extraJobValue = extraJobField.getText();
            String propertySaleValue = propertySaleField.getText();
            String transferValue = transferField.getText();
            String giftValue = giftField.getText();
            String royaltieValue = royaltieField.getText();

            double doubMainJobValue = 0.0;
            double doubExtraJobValue = 0.0;
            double doubPropertySaleValue = 0.0;
            double doubTransferValue = 0.0;
            double doubGiftValue = 0.0;
            double doubRoyaltieValue = 0.0;
            double totalValue = 0.0;

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");

            try {
                if (!mainJobValue.isEmpty()) {
                    doubMainJobValue = Double.parseDouble(mainJobValue);
                    if (doubMainJobValue < 0) throw new NumberFormatException("Basic income cannot be negative");
                }

                if (!extraJobValue.isEmpty()) {
                    doubExtraJobValue = Double.parseDouble(extraJobValue);
                    if (doubExtraJobValue < 0) throw new NumberFormatException("Additional income cannot be negative");
                }

                if (!propertySaleValue.isEmpty()) {
                    doubPropertySaleValue = Double.parseDouble(propertySaleValue);
                    if (doubPropertySaleValue < 0) throw new NumberFormatException("Income from the sale of property cannot be negative");
                }

                if (!transferValue.isEmpty()) {
                    doubTransferValue = Double.parseDouble(transferValue);
                    if (doubTransferValue < 0) throw new NumberFormatException("Transfers cannot be negative");
                }

                if (!giftValue.isEmpty()) {
                    doubGiftValue = Double.parseDouble(giftValue);
                    if (doubGiftValue < 0) throw new NumberFormatException("Gifts cannot be negative");
                }

                if (!royaltieValue.isEmpty()) {
                    doubRoyaltieValue = Double.parseDouble(royaltieValue);
                    if (doubRoyaltieValue < 0) throw new NumberFormatException("Royalties cannot be negative");
                }

                totalValue = doubMainJobValue + doubExtraJobValue + doubPropertySaleValue +
                        doubTransferValue + doubGiftValue + doubRoyaltieValue;

            } catch (NumberFormatException e) {
                alert.setHeaderText("Oops! Incorrect input.");
                alert.setContentText("Check the values: " + e.getMessage());
                alert.showAndWait();
                return;
            }

            Data mainJob = new Data("Main job", doubMainJobValue);
            Data extraJob = new Data("Extra job", doubExtraJobValue);
            Data propertySale = new Data("Sale of property", doubPropertySaleValue);
            Data transfer = new Data("Foreign transfers", doubTransferValue);
            Data gift = new Data("Gifts", doubGiftValue);
            Data royaltie =  new Data("Royalties", doubRoyaltieValue);
            Data total = new Data("Total", totalValue);

            List<Data> data = new ArrayList<>();
            data.add(mainJob);
            data.add(extraJob);
            data.add(propertySale);
            data.add(transfer);
            data.add(gift);
            data.add(royaltie);
            data.add(total);


            if (benefit.isSelected()) {
                for (Data item : data) {
                    if (item.getName().equals("Main job")) {
                        item.setTax(item.getValue() * benefitValue );
                    } else if (!item.getName().equals("Total")) {
                        item.setTax(item.getValue() * generalValue);
                    }
                    if (item.getName().equals("Total")) {
                        item.setTax(totalValue * generalValue - doubMainJobValue * benefitValue);
                    }
                }
            } else {
                for (Data item : data) {
                    double sum = 0;
                    sum += item.getValue();
                    if (!item.getName().equals("Total")) {
                        item.setTax(item.getValue() * generalValue);
                    }
                    if (item.getName().equals("Total")) {
                        item.setTax(sum * generalValue);
                    }
                }
            }
            currentData = data;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("physTable.fxml"));
                Scene scene = new Scene(loader.load(), 515, 400);
                scene.getStylesheets().add(getClass().getResource("/org/example/calc_shit/stylePhysResult.css").toExternalForm());

                PhysTableController controller = loader.getController();
                controller.setData(data);

                Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("logo.png")));
                Stage physTable = new Stage();
                physTable.getIcons().add(icon);
                physTable.setTitle("Taxulator");
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
        Sheet sheet = workbook.createSheet("Tax");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Source of income");
        header.createCell(1).setCellValue("Amount of income");
        header.createCell(2).setCellValue("Amount of tax");

        int rowNum = 1;
        for (Data item : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item.getName());
            row.createCell(1).setCellValue(item.getValue());
            row.createCell(2).setCellValue(item.getTax());
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as Excel File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel files", "*.xlsx"));
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
