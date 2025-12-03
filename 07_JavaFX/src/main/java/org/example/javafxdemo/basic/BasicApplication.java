package org.example.javafxdemo.basic;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BasicApplication extends Application {

    private int counter = 0;

    @Override
    public void start(Stage stage) throws Exception {
        VBox vbox = new VBox();
        Label label = new Label(String.valueOf(counter));
        Button button = new Button("+1");
        button.setOnAction(action -> {
            counter++;
            label.setText(String.valueOf(counter));
        });
        vbox.getChildren().addAll(label, button);
        vbox.setPadding(new Insets(30.0));
        vbox.setSpacing(10.0);
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
