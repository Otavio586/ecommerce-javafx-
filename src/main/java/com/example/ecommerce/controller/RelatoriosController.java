package com.example.ecommerce.controller;

import com.example.ecommerce.dao.VendaDAO;
import com.example.ecommerce.model.Venda;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RelatoriosController {

    @FXML private TableView<Venda> tabelaRelatorio;
    @FXML private TableColumn<Venda, Integer> colId;
    @FXML private TableColumn<Venda, String> colProduto;
    @FXML private TableColumn<Venda, Integer> colQtd;
    @FXML private TableColumn<Venda, Double> colTotal;
    @FXML private TableColumn<Venda, String> colData;

    @FXML private DatePicker dpDataInicio;
    @FXML private DatePicker dpDataFim;

    private VendaDAO dao = new VendaDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProduto.setCellValueFactory(new PropertyValueFactory<>("produtoNome"));
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));

        atualizarRelatorio();
    }

    @FXML
    public void filtrarVendas() {
        LocalDate inicio = dpDataInicio.getValue();
        LocalDate fim = dpDataFim.getValue();

        if (inicio == null || fim == null) {
            return;
        }

        try {
            List<Venda> filtradas = dao.listarPorPeriodo(inicio, fim);
            tabelaRelatorio.setItems(FXCollections.observableArrayList(filtradas));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void atualizarRelatorio() {
        try {
            dpDataInicio.setValue(null);
            dpDataFim.setValue(null);
            tabelaRelatorio.setItems(FXCollections.observableArrayList(dao.listarTodas()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}