package dao;

import model.FolhaPagamento;
import model.Funcionario;
import persistence.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FolhaPagamentoDao {

    public FolhaPagamentoDao() {}

    // Insere uma nova folha de pagamento no banco
    public void inserir(FolhaPagamento folhaPagamento) {
        String sql = """
            INSERT INTO trabalho_final_poo.folha_pagamento
            (funcionario_id, dataPagamento, descontoINSS, descontoIR, salarioLiquido)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, folhaPagamento.getFuncionario().getCodigo());
            stmt.setDate(2, Date.valueOf(folhaPagamento.getDataPagamento()));
            stmt.setDouble(3, folhaPagamento.getDescontoINSS());
            stmt.setDouble(4, folhaPagamento.getDescontoIR());
            stmt.setDouble(5, folhaPagamento.getSalarioLiquido());

            stmt.executeUpdate();

            // Recupera o ID gerado automaticamente
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    folhaPagamento.setCodigo(rs.getInt(1));
                }
            }

            System.out.println("Folha de Pagamento [" + folhaPagamento.getCodigo() + "] adicionada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao inserir folha de pagamento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Lista todas as folhas de pagamento vinculando aos funcionários passados na lista
    public List<FolhaPagamento> listarTodos(List<Funcionario> funcionarios) {
        List<FolhaPagamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM trabalho_final_poo.folha_pagamento";

        try (Connection conn = new ConnectionFactory().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int funcionarioId = rs.getInt("funcionario_id");

                // Procura o funcionário correspondente na lista
                Funcionario funcionario = null;
                for (Funcionario f : funcionarios) {
                    if (f.getCodigo() == funcionarioId) {
                        funcionario = f;
                        break;
                    }
                }

                if (funcionario != null) {
                    FolhaPagamento fp = new FolhaPagamento(
                            funcionario,
                            rs.getDate("dataPagamento").toLocalDate(),
                            rs.getDouble("descontoINSS"),
                            rs.getDouble("descontoIR"),
                            rs.getDouble("salarioLiquido")
                    );
                    fp.setCodigo(rs.getInt("codigo")); // Coluna correta do ID
                    lista.add(fp);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar folhas de pagamento: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }
}
