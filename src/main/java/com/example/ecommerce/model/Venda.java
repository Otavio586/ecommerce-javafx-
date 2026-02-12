package com.example.ecommerce.model;

import java.time.LocalDate;

public class Venda {
    private int id;
    private String produtoNome;
    private int quantidade;
    private double valorTotal;
    private String data;

    public Venda() {
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getProdutoNome() { return produtoNome; }
    public void setProdutoNome(String produtoNome) { this.produtoNome = produtoNome; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
}