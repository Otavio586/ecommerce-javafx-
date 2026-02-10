package com.example.ecommerce.controller;

import com.example.ecommerce.dao.ProdutoDAO;
import com.example.ecommerce.model.Produto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProdutosController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPreco;

    @FXML
    private TextField txtQuantidade;

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TableColumn<Produto, Integer> colId;

    @FXML
    private TableColumn<Produto, String> colNome;

    @FXML
    private TableColumn<Produto, Double> colPreco;

    @FXML
    private TableColumn<Produto, Integer> colQuantidade;

    private ProdutoDAO dao = new ProdutoDAO();

    private Produto produtoSelecionado;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        atualizarTabela();
    }

    private void atualizarTabela() {
        try {
            tabelaProdutos.setItems(FXCollections.observableArrayList(dao.listarTodos()));
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    public void salvarProduto() {
        try {
            String nome = txtNome.getText();
            double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
            int qtd = Integer.parseInt(txtQuantidade.getText());

            if (produtoSelecionado == null) {
                Produto novo = new Produto(nome, preco);
                novo.setQuantidade(qtd);
                dao.salvar(novo);
            } else {
                produtoSelecionado.setNome(nome);
                produtoSelecionado.setPreco(preco);
                produtoSelecionado.setQuantidade(qtd);

                dao.atualizar(produtoSelecionado);
            }

            atualizarTabela();
            limparCampos();
            exibirAlerta("Sucesso", "Operação realizada com sucesso!");

        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao processar: " + e.getMessage());
        }
    }

    @FXML
    public void excluirProduto() {
        if (produtoSelecionado == null) {
            exibirAlerta("Seleção", "Selecione um produto na tabela para excluir.");
            return;
        }

        try {
            dao.deletar(produtoSelecionado.getId());
            atualizarTabela();
            limparCampos();
        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao excluir: " + e.getMessage());
        }
    }

    @FXML
    public void selecionarItem() {
        produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            txtNome.setText(produtoSelecionado.getNome());
            txtPreco.setText(String.valueOf(produtoSelecionado.getPreco()));
            txtQuantidade.setText(String.valueOf(produtoSelecionado.getQuantidade()));
        }
    }

    @FXML
    public void limparCampos() {
        txtNome.clear();
        txtPreco.clear();
        txtQuantidade.clear();
        produtoSelecionado = null;
        tabelaProdutos.getSelectionModel().clearSelection();
    }

    private void exibirAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.show();
    }
}


