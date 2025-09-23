package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {

    private String url = "jdbc:postgresql://localhost:5432/curso";
    private String usuario = "postgres";
    private String senha = "2300";

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, usuario, senha);
            if (conn != null) {
                try (Statement stmt = conn.createStatement()) {
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
                    stmt.executeUpdate(sql);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
