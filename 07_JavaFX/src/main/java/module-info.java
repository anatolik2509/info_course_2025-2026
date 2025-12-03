module org.example.javafxdemo {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.javafxdemo.fxml to javafx.fxml;
    exports org.example.javafxdemo.fxml;
    exports org.example.javafxdemo.basic;
}