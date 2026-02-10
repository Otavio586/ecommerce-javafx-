package com.example.ecommerce.controller;

import com.example.ecommerce.dao.ClienteDAO;
import com.example.ecommerce.model.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;

public class ClientesController {


    @FXML private TextField txtNome, txtCpf, txtTelefone;
    @FXML private TextField txtRua, txtNumero, txtBairro, txtCidade, txtEstado, txtCep;


    @FXML private TableView<Cliente> tabelaClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNome, colCpf, colCidade, colCep;

    private ClienteDAO dao = new ClienteDAO();
    private Cliente clienteSelecionado;

    @FXML
    public void initialize() {
        // Configura as colunas da tabela para ler os atributos da classe Cliente
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        colCep.setCellValueFactory(new PropertyValueFactory<>("cep"));

        atualizarTabela();
    }

    @FXML
    public void salvarCliente() {
        try {
            // Validação básica
            if (txtNome.getText().isEmpty() || txtCpf.getText().isEmpty()) {
                exibirAlerta("Atenção", "Nome e CPF são obrigatórios!", Alert.AlertType.WARNING);
                return;
            }


            Cliente c = (clienteSelecionado == null) ? new Cliente() : clienteSelecionado;


            c.setNome(txtNome.getText());
            c.setCpf(txtCpf.getText());
            c.setTelefone(txtTelefone.getText());
            c.setRua(txtRua.getText());
            c.setNumero(txtNumero.getText());
            c.setBairro(txtBairro.getText());
            c.setCidade(txtCidade.getText());
            c.setEstado(txtEstado.getText());
            c.setCep(txtCep.getText());

            if (clienteSelecionado == null) {
                dao.salvar(c);
                exibirAlerta("Sucesso", "Cliente cadastrado!", Alert.AlertType.INFORMATION);
            } else {
                dao.atualizar(c);
                exibirAlerta("Sucesso", "Dados do cliente atualizados!", Alert.AlertType.INFORMATION);
            }

            atualizarTabela();
            limparCampos();

        } catch (SQLException e) {
            exibirAlerta("Erro", "Falha ao processar no banco: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void selecionarItem() {
        clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null) {
            txtNome.setText(clienteSelecionado.getNome());
            txtCpf.setText(clienteSelecionado.getCpf());
            txtTelefone.setText(clienteSelecionado.getTelefone());
            txtRua.setText(clienteSelecionado.getRua());
            txtNumero.setText(clienteSelecionado.getNumero());
            txtBairro.setText(clienteSelecionado.getBairro());
            txtCidade.setText(clienteSelecionado.getCidade());
            txtEstado.setText(clienteSelecionado.getEstado());
            txtCep.setText(clienteSelecionado.getCep());
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
            c.setCep(txtCep.getText());
            c.setRua(txtRua.getText());
            c.setNumero(txtNumero.getText());
            c.setBairro(txtBairro.getText());
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
            tabelaClientes.getItems().setAll(dao.listarTodos());
        } catch (SQLException e) {
            System.err.println("Erro ao carregar tabela: " + e.getMessage());
        }
    }

    private void limparCampos() {
        txtNome.clear();
        txtCpf.clear();
        txtTelefone.clear();
        txtRua.clear();
        txtNumero.clear();
        txtBairro.clear();
        txtCidade.clear();
        txtEstado.clear();
        txtCep.clear();
        clienteSelecionado = null;
    }

    private void exibirAlerta(String titulo, String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}