package com.example.ecommerce.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MainController {

    @FXML
    private BorderPane root;

    @FXML
    public void initialize() {
    }

    @FXML
    public void abrirProdutos(ActionEvent event) {
        carregarTela("Produtos.fxml");
    }

    @FXML
    public void abrirClientes(ActionEvent event) {
        carregarTela("Clientes.fxml");
    }
    @FXML
    public void abrirHome(ActionEvent event) {
        carregarTela("Home.fxml");
    }

    public void abrirAjuda(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sobre o sistema");
        alert.setHeaderText("Informações do sistema");
        alert.setContentText(
                "Nome: Sistema de Reservas\n" +
                        "Versão: 1.0.0\n" +
                        "Desenvolvedor: Otávio Stevan\n" +
                        "Ano: 2026"
        );
        alert.showAndWait();
    }

    private void carregarTela(String fxml) {
        try {
            java.net.URL resource = getClass().getResource("/com/example/ecommerce/" + fxml);

            if (resource != null) {
                Node novaTela = FXMLLoader.load(resource);
                root.setCenter(novaTela);
            } else {
                System.err.println("FXML não encontrado: " + fxml);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}