package com.example.ecommerce.controller;

import com.example.ecommerce.dao.ProdutoDAO;
import com.example.ecommerce.dao.VendaDAO; // Importação do novo DAO
import com.example.ecommerce.model.Produto;
import com.example.ecommerce.model.Venda;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;

public class VendasController {

    @FXML private ComboBox<Produto> cbProdutos;
    @FXML private TextField txtQuantidade;
    @FXML private TableView<Venda> tabelaVendas;
    @FXML private TableColumn<Venda, String> colProduto;
    @FXML private TableColumn<Venda, Integer> colQtd;
    @FXML private TableColumn<Venda, Double> colTotal;
    @FXML private TableColumn<Venda, String> colData;

    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private VendaDAO vendaDAO = new VendaDAO();

    @FXML
    public void initialize() {
        configurarColunas();
        carregarProdutos();
        atualizarTabela();
    }

    private void configurarColunas() {
        colProduto.setCellValueFactory(new PropertyValueFactory<>("produtoNome"));
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
    }

    private void carregarProdutos() {
        try {
            cbProdutos.setItems(FXCollections.observableArrayList(produtoDAO.listarTodos()));
            cbProdutos.setCellFactory(lv -> new ListCell<>() {
                @Override protected void updateItem(Produto p, boolean empty) {
                    super.updateItem(p, empty);
                    setText(empty ? "" : p.getNome() + " - R$ " + p.getPreco());
                }
            });
            cbProdutos.setButtonCell(cbProdutos.getCellFactory().call(null));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void atualizarTabela() {
        try {
            tabelaVendas.setItems(FXCollections.observableArrayList(vendaDAO.listarTodas()));
        } catch (SQLException e) {
            exibirAlerta("Erro", "Erro ao carregar vendas: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void registrarVenda() {
        Produto selecionado = cbProdutos.getSelectionModel().getSelectedItem();
        String qtdTexto = txtQuantidade.getText();

        if (selecionado == null || qtdTexto.isEmpty()) {
            exibirAlerta("Atenção", "Selecione um produto e a quantidade.", Alert.AlertType.WARNING);
            return;
        }

        try {
            int qtdVendida = Integer.parseInt(qtdTexto);
            if (selecionado.getQuantidade() < qtdVendida) {
                exibirAlerta("Estoque Insuficiente",
                        "Você tentou vender " + qtdVendida + " unidades, mas só existem " + selecionado.getQuantidade() + " em estoque.",
                        Alert.AlertType.ERROR);
                return;
            }

            Venda v = new Venda();
            v.setProdutoNome(selecionado.getNome());
            v.setQuantidade(qtdVendida);
            v.setValorTotal(selecionado.getPreco() * qtdVendida);
            vendaDAO.salvar(v);

            int novoEstoque = selecionado.getQuantidade() - qtdVendida;
            selecionado.setQuantidade(novoEstoque);


            produtoDAO.atualizar(selecionado);

            atualizarTabela();
            carregarProdutos();

            txtQuantidade.clear();
            cbProdutos.getSelectionModel().clearSelection();

            exibirAlerta("Sucesso", "Venda realizada! Estoque atualizado: " + novoEstoque + " unidades restantes.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            exibirAlerta("Erro", "Quantidade inválida.", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            exibirAlerta("Erro de Banco", "Erro ao processar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void excluirVenda() {
        Venda selecionada = tabelaVendas.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            exibirAlerta("Seleção", "Selecione uma venda na tabela para excluir.", Alert.AlertType.WARNING);
            return;
        }

        VendaDAO.deletar(selecionada.getId());
        atualizarTabela();
        exibirAlerta("Sucesso", "Venda removida do histórico.", Alert.AlertType.INFORMATION);
    }

    private void exibirAlerta(String titulo, String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}