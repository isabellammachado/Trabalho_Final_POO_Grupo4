package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import persistence.ConnectionFactory;

public class FolhaPagamentoService {

    // Gera automaticamente a folha de pagamento para todos os funcionários
    public void gerarFolhaPagamento() {
        try (Connection conn = new ConnectionFactory().getConnection()) {

            // Seleciona todos os funcionários
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, salario_bruto FROM trabalho_final_poo.funcionario");

            // Para cada funcionário, calcula os descontos e insere na folha
            while (rs.next()) {
                int id = rs.getInt("id");
                double salarioBruto = rs.getDouble("salario_bruto");

                double descontoINSS = salarioBruto * 0.11; // Exemplo: 11%
                double descontoIR = salarioBruto * 0.075;   // Exemplo: 7,5%
                double salarioLiquido = salarioBruto - descontoINSS - descontoIR;

                // Insere os dados na tabela folha_pagamento
                String sql = """
                    INSERT INTO trabalho_final_poo.folha_pagamento
                    (funcionario_id, dataPagamento, descontoINSS, descontoIR, salarioLiquido)
                    VALUES (?, ?, ?, ?, ?)
                """;

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ps.setObject(2, LocalDate.now());
                    ps.setDouble(3, descontoINSS);
                    ps.setDouble(4, descontoIR);
                    ps.setDouble(5, salarioLiquido);
                    ps.executeUpdate();
                }
            }

            System.out.println("Folha de pagamento gerada com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao gerar folha de pagamento: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
