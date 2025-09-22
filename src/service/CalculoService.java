package service;

import java.time.LocalDate;

import model.FolhaPagamento;
import model.Funcionario;

public class CalculoService implements ICalculoService{

	public CalculoService() {}

	@Override
	public double calcularIR(int qtdDependentes, double salarioBruto) {
		// cIR = ((SlBruto -DeduçãoDependentes) +AliquotaIR)-DeduçãoIR
		double deducaoDependente = 189.59;
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
	public double calcularINSS(double salarioBruto) {
		double descontoINSS = 0.0;

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
	public double calcularSalarioLiquido(double salarioBruto, int qtdDependentes) {
		double descontoINSS = calcularINSS(salarioBruto);
		double descontoIR = calcularIR(qtdDependentes, salarioBruto);
		return salarioBruto - descontoINSS - descontoIR;
	}
}