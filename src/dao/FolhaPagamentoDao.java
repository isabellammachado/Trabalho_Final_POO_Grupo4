package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.FolhaPagamento;
import persistence.ConnectionFactory;

public class FolhaPagamentoDao {

    public FolhaPagamentoDao() {}

    // Inserir Folha de Pagamento
    public void inserir(FolhaPagamento folhaPagamento) throws SQLException {

        // Query SQL
        String sql = "INSERT INTO folha_pagamento(codigo, funcionario_id, data_pagamento, desconto_inss, desconto_ir, salario_liquido) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, folhaPagamento.getCodigo());
            // Salvamos o CPF do Funcionário como FK na FolhaPagamento
            stmt.setInt(2, folhaPagamento.getFuncionario().getCodigo());
            stmt.setDate(3, Date.valueOf(folhaPagamento.getDataPagamento()));
            stmt.setDouble(4, folhaPagamento.getDescontoINSS());
            stmt.setDouble(5, folhaPagamento.getDescontoIR());
            stmt.setDouble(6, folhaPagamento.getSalarioLiquido());

            stmt.executeUpdate();
        }
    }
}