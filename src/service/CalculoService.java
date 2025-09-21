package service;

import java.time.LocalDate;

import model.FolhaPagamento;
import model.Funcionario;

public class CalculoService extends FolhaPagamento implements ICalculoService{

	public CalculoService(int codigo, Funcionario funcionario, LocalDate dataPagamento, double descontoINSS,
			double descontoIR, double salarioLiquido) {
		super(codigo, funcionario, dataPagamento, descontoINSS, descontoIR, salarioLiquido);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double calcularIR() {
		// cIR = ((SlBruto -DeduçãoDependentes) +AliquotaIR)-DeduçãoIR
		double deducaoDependente = 189.59;
		int qtdDependentes = funcionario.getDependentes().size(); 
		double salarioBruto = funcionario.getSalarioBruto(); 
		double totalDeducao = qtdDependentes * deducaoDependente;
		double descontoIR;

		if (salarioBruto <= 2259.20) {
			descontoIR = 0.0;
		} else if (salarioBruto <= 2826.65) {
			descontoIR = ((salarioBruto - totalDeducao) * 0.075 - 169.44);
		} else if (salarioBruto <= 3751.05) {
			descontoIR = ((salarioBruto - totalDeducao) * 0.15 - 381.44);
		} else if (salarioBruto <= 4664.68) {
			descontoIR = ((salarioBruto - totalDeducao) * 0.225 - 662.77);
		} else {
			descontoIR = ((salarioBruto - totalDeducao) * 0.275 - 896.00);
		}
		return descontoIR;
	}

	@Override
	public double calcularINSS() {
		double salarioBruto = funcionario.getSalarioBruto();
		double descontoINSS;

		if (salarioBruto <= 1412.00) {
			descontoINSS = salarioBruto * 0.075;
		} else if (salarioBruto <= 2666.68) {
			descontoINSS = salarioBruto * 0.09;
		} else if (salarioBruto <= 4000.03) {
			descontoINSS = salarioBruto * 0.12;
		} else if (salarioBruto <= 7786.02) {
			descontoINSS = salarioBruto * 0.14;
		} else {
			descontoINSS = 7786.02 * 0.14; // teto do INSS
		}
		return descontoINSS;
	}

	@Override
	public double calcularSalarioLiquido() {
		double salarioBruto = funcionario.getSalarioBruto();
		double descontoINSS = calcularINSS();
		double descontoIR = calcularIR();
		return salarioBruto - descontoINSS - descontoIR;
	}
}