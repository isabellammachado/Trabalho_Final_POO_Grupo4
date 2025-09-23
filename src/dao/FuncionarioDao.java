package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Funcionario;
import persistence.ConnectionFactory;

public class FuncionarioDao {

	public FuncionarioDao() {}

	
    public void inserir(Funcionario funcionario) throws SQLException {
        
        String sql = "INSERT INTO trabalho_final_poo.funcionario(nome, cpf, data_nascimento, salario_bruto, desconto_inss, desconto_ir) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
//            stmt.setInt(1, funcionario.getCodigo());
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
            stmt.setDouble(4, funcionario.getSalarioBruto());
            stmt.setDouble(5, funcionario.getDescontoINSS());
            stmt.setDouble(6, funcionario.getDescontoIR());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                funcionario.setCodigo(rs.getInt(1));
            }
        }
        System.out.println("Funcionário [" + funcionario.getCodigo() + "] adicionado com sucesso!");
    }

	public void atualizar(Funcionario funcionario, int codigo) {
		String sql = "UPDATE funcionarios SET nome=?, cpf=?, data_nascimento=?, salario_bruto=? WHERE codigo=?";

		try (Connection conn = new ConnectionFactory().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			stmt.setObject(3, funcionario.getDataNascimento());
			stmt.setDouble(4, funcionario.getSalarioBruto());

			stmt.setInt(5, codigo);

			stmt.executeUpdate();
			stmt.close();
			
		} catch (SQLException e) {
			
			System.err.println("Erro ao atualizar registro de funcionário!");
			e.printStackTrace();
		}
	}

	// Remover funcionário
	public void remover(int codigo) {
		String sql = "DELETE FROM funcionarios WHERE codigo=?";
		try (Connection conn = new ConnectionFactory().getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setInt(1, codigo);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("Erro ao remover registro de funcionário!");
			e.printStackTrace();
		}
	}

	public List<Funcionario> listar() {
		String sql = "SELECT * FROM funcionarios";
		List<Funcionario> funcionarios = new ArrayList<>();

		try (Connection conn = new ConnectionFactory().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Funcionario f = new Funcionario(rs.getString("nome"), rs.getString("cpf"),
						rs.getObject("data_nascimento", LocalDate.class), rs.getDouble("salario_bruto") );
				funcionarios.add(f);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("Erro ao listar funcionários!");
			e.printStackTrace();
		}

		return funcionarios;
	}
}
