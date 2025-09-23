package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {

    // Dados de conexão
    private static final String URL = "jdbc:postgresql://localhost:5432/curso";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "2300";

    // Retorna uma conexão com o banco
    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            if (conn != null) {
                criarTabelas(conn); // Cria schema e tabelas se não existirem
            }
            return conn;
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Cria o schema e tabelas do sistema
    private void criarTabelas(Connection conn) {
        String sql = """
            CREATE SCHEMA IF NOT EXISTS trabalho_final_poo;

            CREATE TABLE IF NOT EXISTS trabalho_final_poo.funcionario (
                id SERIAL PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                cpf CHAR(11) NOT NULL UNIQUE,
                data_nascimento DATE NOT NULL,
                salario_bruto NUMERIC(10,2) NOT NULL,
                desconto_inss NUMERIC(10,2) NOT NULL,
                desconto_ir NUMERIC(10,2) NOT NULL
            );

            CREATE TABLE IF NOT EXISTS trabalho_final_poo.dependente (
                id SERIAL PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                cpf CHAR(11) NOT NULL UNIQUE,
                data_nascimento DATE NOT NULL,
                parentesco CHAR(15),
                funcionario_id INT NOT NULL,
                CONSTRAINT fk_funcionario
                FOREIGN KEY (funcionario_id) REFERENCES trabalho_final_poo.funcionario(id)
            );

            CREATE TABLE IF NOT EXISTS trabalho_final_poo.folha_pagamento (
                codigo SERIAL PRIMARY KEY,
                funcionario_id INT NOT NULL,
                dataPagamento DATE NOT NULL,
                descontoINSS NUMERIC(10,2),
                descontoIR NUMERIC(10,2),
                salarioLiquido NUMERIC(10,2),
                FOREIGN KEY(funcionario_id) REFERENCES trabalho_final_poo.funcionario(id)
            );
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
