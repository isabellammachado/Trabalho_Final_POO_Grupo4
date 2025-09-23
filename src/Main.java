import dao.FolhaPagamentoDao;
import dao.FuncionarioDao;
import model.FolhaPagamento;
import model.Funcionario;
import service.ArquivoService;
import service.CalculoService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
    	
        ArquivoService arquivoService = new ArquivoService();
        CalculoService calculoService = new CalculoService();
        FuncionarioDao funcionarioDao = new FuncionarioDao();
        FolhaPagamentoDao folhaPagamentoDao = new FolhaPagamentoDao();

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        ArrayList<FolhaPagamento> folhaPagamentos = new ArrayList<>();
        int cont = 0;

        funcionarios = arquivoService.carregarFuncionariosDeArquivo();
        for (Funcionario func : funcionarios) {
            int qtdDependentes = func.getDependentes().size();
            double salarioBruto = func.getSalarioBruto();
            func.setDescontoINSS(calculoService.calcularINSS(salarioBruto));
            func.setDescontoIR(calculoService.calcularIR(qtdDependentes, salarioBruto));
            funcionarioDao.inserir(func);

            LocalDate date = LocalDate.now();
            double salarioLiquido = calculoService.calcularSalarioLiquido(func.getSalarioBruto(), func.getDescontoINSS(), func.getDescontoIR());
            FolhaPagamento folhaPagamento = new FolhaPagamento(func, date, func.getDescontoINSS(), func.getDescontoIR(), salarioLiquido);
            folhaPagamentos.add(cont, folhaPagamento);
            folhaPagamentoDao.inserir(folhaPagamento);
            cont++;
        }
        service.ArquivoService.ExportarArquivoTXT(funcionarios, folhaPagamentos);
    }
}
