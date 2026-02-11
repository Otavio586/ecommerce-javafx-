package com.example.ecommerce.controller;

import com.example.ecommerce.dao.ClienteDAO;
import com.example.ecommerce.model.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;

public class ClientesController {


    @FXML private TextField txtNome, txtCpf, txtTelefone, txtEmail, txtCep, txtCidade , txtEstado;


    @FXML private TableView<Cliente> tabelaClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNome, colCpf, colTelefone , colEmail , colCep , colCidade, colEstado;

    private ClienteDAO dao = new ClienteDAO();
    private Cliente clienteSelecionado;

    @FXML
    public void initialize() {

        try {
            dao = new ClienteDAO();
            atualizarTabela();
        } catch (Exception e) {
            exibirAlerta("Erro de Conexão", "Não foi possível conectar ao banco de dados!", Alert.AlertType.ERROR);
        }
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        atualizarTabela();
    }

    @FXML
    public void selecionarItem() {
        clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null) {
            txtNome.setText(clienteSelecionado.getNome());
            txtCpf.setText(clienteSelecionado.getCpf());
            txtTelefone.setText(clienteSelecionado.getTelefone());
            txtEmail.setText(clienteSelecionado.getEmail());
            txtCep.setText(clienteSelecionado.getCep());
            txtCidade.setText(clienteSelecionado.getCidade());
            txtEstado.setText(clienteSelecionado.getEstado());
        }
    }

    @FXML
    public void salvarCliente() {
        try {
            if (txtNome.getText().isEmpty() || txtCpf.getText().isEmpty()) {
                exibirAlerta("Atenção", "Preencha Nome e CPF!", Alert.AlertType.WARNING);
                return;
            }

            Cliente c = (clienteSelecionado == null) ? new Cliente() : clienteSelecionado;

            c.setNome(txtNome.getText());
            c.setCpf(txtCpf.getText());
            c.setTelefone(txtTelefone.getText());
            c.setEmail(txtEmail.getText());
            c.setCep(txtCep.getText());
            c.setCidade(txtCidade.getText());
            c.setEstado(txtEstado.getText());

            if (clienteSelecionado == null) {
                dao.salvar(c);
                exibirAlerta("Sucesso", "Cliente salvo!", Alert.AlertType.INFORMATION);
            } else {
                dao.atualizar(c);
                exibirAlerta("Sucesso", "Cliente atualizado!", Alert.AlertType.INFORMATION);
            }

            atualizarTabela();
            limparCampos();
        } catch (SQLException e) {
            exibirAlerta("Erro", "Erro ao salvar no banco: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void excluirCliente() {
        if (clienteSelecionado == null) {
            exibirAlerta("Erro", "Selecione um cliente na tabela primeiro!", Alert.AlertType.WARNING);
            return;
        }

        try {
            dao.deletar(clienteSelecionado.getId());
            exibirAlerta("Sucesso", "Cliente removido!", Alert.AlertType.INFORMATION);
            atualizarTabela();
            limparCampos();
        } catch (SQLException e) {
            exibirAlerta("Erro", "Erro ao deletar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void atualizarTabela() {
        try {
            if (dao != null) {
                tabelaClientes.getItems().setAll(dao.listarTodos());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar tabela: " + e.getMessage());
        }
    }

    private void limparCampos() {
        txtNome.clear();
        txtCpf.clear();
        txtTelefone.clear();
        txtCep.clear();
        txtEmail.clear();
        txtCidade.clear();
        txtEstado.clear();
        clienteSelecionado = null;
        tabelaClientes.getSelectionModel().clearSelection();
    }

    private void exibirAlerta(String titulo, String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}