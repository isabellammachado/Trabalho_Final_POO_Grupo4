package dao;

import model.Dependente;
import model.Funcionario;
import enums.Parentesco;
import persistence.ConnectionFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import exception.DependenteException;

public class DependenteDao {

    public DependenteDao() {}

    // Inserir dependente
    public void inserir(Dependente dependente, Funcionario funcionario) {
        String sql = "INSERT INTO trabalho_final_poo.dependente(nome, cpf, data_nascimento, parentesco, funcionario_id) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, dependente.getNome());
            stmt.setString(2, dependente.getCpf());
            stmt.setDate(3, Date.valueOf(dependente.getDataNascimento()));
            stmt.setString(4, dependente.getParentesco().name());
            stmt.setInt(5, funcionario.getCodigo());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    dependente.setCodigo(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir dependente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Atualizar dependente
    public void atualizar(Dependente dependente) {
        String sql = "UPDATE trabalho_final_poo.dependente SET nome=?, cpf=?, data_nascimento=?, parentesco=? WHERE id=?";

        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dependente.getNome());
            stmt.setString(2, dependente.getCpf());
            stmt.setDate(3, Date.valueOf(dependente.getDataNascimento()));
            stmt.setString(4, dependente.getParentesco().name());
            stmt.setInt(5, dependente.getCodigo());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar dependente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Remover dependente
    public void remover(int codigo) {
        String sql = "DELETE FROM trabalho_final_poo.dependente WHERE id=?";

        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao remover dependente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Listar dependentes por funcionário
    public List<Dependente> listarPorFuncionario(int funcionarioId) {
        List<Dependente> dependentes = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, data_nascimento, parentesco FROM trabalho_final_poo.dependente WHERE funcionario_id=?";

        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, funcionarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    // Converte string do banco para enum de forma segura
                    String parentescoStr = rs.getString("parentesco");
                    Parentesco parentescoEnum;
                    if (parentescoStr == null || parentescoStr.trim().isEmpty()) {
                        parentescoEnum = Parentesco.OUTROS;
                    } else {
                        switch (parentescoStr.trim().toUpperCase()) {
                            case "FILHO": parentescoEnum = Parentesco.FILHO; break;
                            case "SOBRINHO": parentescoEnum = Parentesco.SOBRINHO; break;
                            case "OUTROS": parentescoEnum = Parentesco.OUTROS; break;
                            default: parentescoEnum = Parentesco.OUTROS; break;
                        }
                    }

                    // Cria o dependente
                    try {
                        Dependente d = new Dependente(
                                rs.getString("nome"),
                                rs.getString("cpf"),
                                rs.getObject("data_nascimento", LocalDate.class),
                                parentescoEnum
                        );
                        d.setCodigo(rs.getInt("id"));
                        dependentes.add(d);
                    } catch (DependenteException e) {
                        System.err.println("Dependente inválido: " + e.getMessage());
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar dependentes: " + e.getMessage());
            e.printStackTrace();
        }

        return dependentes;
    }
}
