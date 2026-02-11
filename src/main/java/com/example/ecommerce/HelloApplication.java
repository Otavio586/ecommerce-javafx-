package com.example.ecommerce;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/ecommerce/MainLayout.fxml"));
        Scene scene = new Scene(loader.load(), 320, 240);
        stage.setTitle("Ecommerce");
        stage.setScene(scene);
        stage.show();
    }
    public static void main (String[] args) {
        launch();
    }

    @FXML
    private void fecharTela(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
