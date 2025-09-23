package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Funcionario;

public class TestaConnection {
    public static void main(String[] args) {
        Connection connection = new ConnectionFactory().getConnection();

        String sql = "select * from trabalho_final_poo.funcionario";

        List<Funcionario> listaFuncionario = new ArrayList<Funcionario>();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
//                int codigo = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");

                java.sql.Date sqlDate = rs.getDate("data_nascimento");
                LocalDate dataNascimento = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                double salarioBruto = rs.getDouble("salario_bruto");

                Funcionario funcionario = new Funcionario(nome, cpf, dataNascimento, salarioBruto);

                listaFuncionario.add(funcionario);

            }
            rs.close();
            connection.close();

            for(Funcionario l :listaFuncionario) {
                System.out.println(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}