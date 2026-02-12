package com.example.ecommerce.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class HomeController {

    @FXML
    public void abrirClientes(ActionEvent event) {
        MainController.getInstance().carregarTela("Clientes.fxml");
    }

    @FXML
    public void abrirProdutos(ActionEvent event) {
        MainController.getInstance().carregarTela("Produtos.fxml");
    }

    @FXML
    public void abrirVendas(ActionEvent event) {
        MainController.getInstance().carregarTela("Vendas.fxml");
    }

    @FXML
    public void abrirRelatorios() {
        MainController.getInstance().carregarTela("Relatorios.fxml");
    }
}
