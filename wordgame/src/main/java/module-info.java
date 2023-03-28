module com.perttu {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.perttu to javafx.fxml;
    exports com.perttu;
}
