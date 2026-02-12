package com.example.ecommerce.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;

import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane root;
    private static MainController instance;

    public MainController() {
        instance = this;
    }

    public static MainController getInstance() {
        return instance;
    }

    @FXML
    public void initialize() {
        carregarTela("Home.fxml");
    }

    @FXML
    public void abrirHome(ActionEvent event) {
        carregarTela("Home.fxml");
    }

    @FXML
    public void abrirClientes(ActionEvent event) {
        carregarTela("Clientes.fxml");
    }

    @FXML
    public void abrirProdutos(ActionEvent event) {
        carregarTela("Produtos.fxml");
    }

    @FXML
    public void abrirVendas(ActionEvent event) {
        carregarTela("Vendas.fxml");
    }

    @FXML
    public void abrirRelatorios() {
        carregarTela("Relatorios.fxml");
    }

    @FXML
    public void abrirAjuda(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sobre o sistema");
        alert.setHeaderText("Informações do sistema");
        alert.setContentText(
                "Sistema Ecommerce\nVersão 1.0\n2026"
        );
        alert.showAndWait();
    }


    public void carregarTela(String fxml) {
        try {
            String caminho = fxml.startsWith("/") ? fxml : "/" + fxml;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));

            Node node = loader.load();

            if (root != null) {
                root.setCenter(node);
            } else {
                System.err.println("Erro: O BorderPane 'root' não foi inicializado!");
            }
        } catch (IOException e) {
            System.err.println("Não foi possível carregar o arquivo FXML: " + fxml);
            e.printStackTrace();
        }
    }
}