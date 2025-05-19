package org.example.calc_shit;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller implements Initializable
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private Text title;

    @FXML
    private Button start;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Font font25 = Font.loadFont(getClass().getResourceAsStream("/fonts/DimboRussian.otf"), 25);
        Font font21 = Font.loadFont(getClass().getResourceAsStream("/fonts/DimboRussian.otf"), 21);
        title.setFont(font25);


        start.setOnAction(event -> {

                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("phys.fxml"));
                    Scene scene = null;
                    try {
                        scene = new Scene(fxmlLoader.load(), 555, 360);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Stage phys = new Stage();
                    Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("logo.png")));
                    phys.getIcons().add(icon);
                    phys.setTitle("Taxulator");
                    String css = Objects.requireNonNull(this.getClass().getResource("stylePhys.css")).toExternalForm();
                    scene.getStylesheets().add(css);
                    phys.setScene(scene);
                    phys.initModality(Modality.APPLICATION_MODAL);
                    phys.setResizable(false);
                    phys.showAndWait();



        });

    }
}
