module org.example.calc_shit {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens org.example.calc_shit to javafx.fxml;
    exports org.example.calc_shit;
}