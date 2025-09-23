package main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.DependenteDao;
import dao.FuncionarioDao;
import exception.DependenteException;
import model.Dependente;
import model.Funcionario;
import view.FuncionarioView;
import persistence.ConnectionFactory;

public class Application {

    public static void main(String[] args) {
        try {
            ConnectionFactory cf = new ConnectionFactory();
            Connection conn = cf.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Conectado com sucesso ao banco de dados!");
            }

            FuncionarioDao funcionarioDao = new FuncionarioDao();
            DependenteDao dependenteDao = new DependenteDao();

            List<Funcionario> funcionarios = funcionarioDao.listar();
            for (Funcionario f : funcionarios) {
                List<Dependente> dependentes = dependenteDao.listarPorFuncionario(f.getCodigo());
                for (Dependente d : dependentes) {
                    f.adicionarDependente(d);
                }
            }

            FuncionarioView view = new FuncionarioView(funcionarios);
            view.selecionarMenu();

        } catch (SQLException e) {
            System.err.println("Erro de conexão ou leitura do banco de dados:");
            e.printStackTrace();
        } catch (DependenteException e) {
            System.err.println("Erro ao criar dependentes:");
            e.printStackTrace();
        }
    }
}
