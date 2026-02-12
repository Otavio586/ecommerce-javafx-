package com.example.ecommerce.dao;

import com.example.ecommerce.model.Venda;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {
    private Connection connection;

    public VendaDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public static void deletar(int id) {
    }

    public void salvar(Venda venda) throws SQLException {
        String sql = "INSERT INTO vendas (produto_nome, quantidade, valor_total) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, venda.getProdutoNome());
            stmt.setInt(2, venda.getQuantidade());
            stmt.setDouble(3, venda.getValorTotal());
            stmt.execute();
        }
    }

    public List<Venda> listarTodas() throws SQLException {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT *, DATE_FORMAT(data_venda, '%d/%m/%Y %H:%i') as data_formatada FROM vendas ORDER BY id DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Venda v = new Venda();
                v.setId(rs.getInt("id"));
                v.setProdutoNome(rs.getString("produto_nome"));
                v.setQuantidade(rs.getInt("quantidade"));
                v.setValorTotal(rs.getDouble("valor_total"));
                v.setData(rs.getString("data_formatada"));
                vendas.add(v);
            }
        }
        return vendas;
    }

    public List<Venda> listarPorPeriodo(LocalDate inicio, LocalDate fim) throws SQLException {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT *, DATE_FORMAT(data_venda, '%d/%m/%Y %H:%i') as data_formatada " +
                "FROM vendas WHERE DATE(data_venda) BETWEEN ? AND ? ORDER BY id DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(inicio));
            stmt.setDate(2, java.sql.Date.valueOf(fim));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Venda v = new Venda();
                    v.setId(rs.getInt("id"));
                    v.setProdutoNome(rs.getString("produto_nome"));
                    v.setQuantidade(rs.getInt("quantidade"));
                    v.setValorTotal(rs.getDouble("valor_total"));
                    v.setData(rs.getString("data_formatada"));
                    vendas.add(v);
                }
            }
        }
        return vendas;
    }
}