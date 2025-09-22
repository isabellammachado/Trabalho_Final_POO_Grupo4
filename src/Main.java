import dao.FuncionarioDao;
import model.Funcionario;
import service.ArquivoService;
import service.CalculoService;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
        ArquivoService arquivoService = new ArquivoService();
        CalculoService calculoService = new CalculoService();
        FuncionarioDao funcionarioDao = new FuncionarioDao();
        ArrayList<Funcionario> funcionarios = new ArrayList<>();

        funcionarios = arquivoService.carregarFuncionariosDeArquivo();
        for (Funcionario func : funcionarios) {
            int qtdDependentes = func.getDependentes().size();
            double salarioBruto = func.getSalarioBruto();
            func.setDescontoINSS(calculoService.calcularINSS(salarioBruto));
            func.setDescontoIR(calculoService.calcularIR(qtdDependentes, salarioBruto));

            funcionarioDao.inserir(func);
        }

    }
}
