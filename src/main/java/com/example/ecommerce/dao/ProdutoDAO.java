package com.example.ecommerce.dao;

import com.example.ecommerce.model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private Connection connection;

    public ProdutoDAO(){
        this.connection = new ConnectionFactory().getConnection();
    }

    public void salvar(Produto produto) throws SQLException {

        String sql = "INSERT INTO produtos (`Produto`, `Preço`, `Quantidade`) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.execute();
        }
    }

    public List<Produto> listarTodos() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("ID"));
                p.setNome(rs.getString("Produto"));
                p.setPreco(rs.getDouble("Preço"));
                p.setQuantidade(rs.getInt("Quantidade"));
                produtos.add(p);
            }
        }
        return produtos;
    }
    public void atualizar(Produto p) throws SQLException {
        String sql = "UPDATE produtos SET Produto = ?, `Preço` = ?, Quantidade = ? WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setDouble(2, p.getPreco());
            stmt.setInt(3, p.getQuantidade());
            stmt.setInt(4, p.getId());

            stmt.execute();
        }
    }
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM produtos WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }
}
