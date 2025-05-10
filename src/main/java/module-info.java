module org.example.calc_shit {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.calc_shit to javafx.fxml;
    exports org.example.calc_shit;
}