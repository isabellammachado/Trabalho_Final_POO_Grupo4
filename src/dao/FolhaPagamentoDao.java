package dao;

import model.FolhaPagamento;
import persistence.ConnectionFactory;

import java.sql.*;

public class FolhaPagamentoDao {

    public FolhaPagamentoDao() {}

    // Inserir Folha de Pagamento
    public void inserir(FolhaPagamento folhaPagamento) throws SQLException {

        // Query SQL
        String sql = "INSERT INTO trabalho_final_poo.folha_pagamento(funcionario_id, dataPagamento, descontoINSS, descontoIR, salarioLiquido) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

//            stmt.setLong(1, folhaPagamento.getCodigo());
            stmt.setInt(1, folhaPagamento.getFuncionario().getCodigo());
            stmt.setDate(2, Date.valueOf(folhaPagamento.getDataPagamento()));
            stmt.setDouble(3, folhaPagamento.getDescontoINSS());
            stmt.setDouble(4, folhaPagamento.getDescontoIR());
            stmt.setDouble(5, folhaPagamento.getSalarioLiquido());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                folhaPagamento.setCodigo(rs.getInt(1));
            }
        }
        System.out.println("Folha de Pagamento [" + folhaPagamento.getCodigo() + "] adicionada com sucesso!");

    }
}