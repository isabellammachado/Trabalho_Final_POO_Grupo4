package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Funcionario;
import persistence.ConnectionFactory;

public class FuncionarioDao {

	public FuncionarioDao() {}

	// Inserir funcionário
    public void inserir(Funcionario funcionario) throws SQLException {
        // Query SQL
        String sql = "INSERT INTO funcionarios(nome, cpf, data_nascimento, salario_bruto, desconto_inss, desconto_ir) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        // Use o try-with-resources para garantir que a conexão e o statement sejam fechados
        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
            stmt.setDouble(4, funcionario.getSalarioBruto());
            stmt.setDouble(5, funcionario.getDescontoINSS());
            stmt.setDouble(6, funcionario.getDescontoIR());

            stmt.executeUpdate();
        }
    }

	// Atualizar funcionário
//	public void atualizar(Funcionario funcionario, int codigo) {
//		String sql = "UPDATE funcionarios SET nome=?, cpf=?, data_nascimento=?, salario_bruto=?, ? "
//				+ "WHERE codigo=?";
//		try {
//			PreparedStatement stmt = connection.prepareStatement(sql);
//			stmt.setString(1, funcionario.getNome());
//			stmt.setString(2, funcionario.getCpf());
//			stmt.setObject(3, funcionario.getDataNascimento());
//			stmt.setDouble(4, funcionario.getSalarioBruto());
//
//			stmt.setInt(7, codigo);
//
//			stmt.executeUpdate();
//			stmt.close();
//		} catch (SQLException e) {
//			System.err.println("Erro ao atualizar registro de funcionário!");
//			e.printStackTrace();
//		}
//	}
//
//	// Remover funcionário
//	public void remover(int codigo) {
//		String sql = "DELETE FROM funcionarios WHERE codigo=?";
//		try {
//			PreparedStatement stmt = connection.prepareStatement(sql);
//			stmt.setInt(1, codigo);
//			stmt.executeUpdate();
//			stmt.close();
//		} catch (SQLException e) {
//			System.err.println("Erro ao remover registro de funcionário!");
//			e.printStackTrace();
//		}
//	}
//
//	// Listar todos os funcionários
//	public List<Funcionario> listar() {
//		String sql = "SELECT * FROM funcionarios";
//		List<Funcionario> funcionarios = new ArrayList<>();
//
//		try {
//			PreparedStatement stmt = connection.prepareStatement(sql);
//			ResultSet rs = stmt.executeQuery();
//
//			while (rs.next()) {
//				Funcionario f = new Funcionario(rs.getString("nome"), rs.getString("cpf"),
//						rs.getObject("data_nascimento", LocalDate.class), rs.getDouble("salario_bruto"),
//						rs.getDouble("desconto_inss"), rs.getDouble("desconto_ir"), null );
//				funcionarios.add(f);
//			}
//
//			rs.close();
//			stmt.close();
//		} catch (SQLException e) {
//			System.err.println("Erro ao listar funcionários!");
//			e.printStackTrace();
//		}
//
//		return funcionarios;
//	}
}
