package service;

public interface ICalculoService {
	 double calcularINSS(double salarioBruto);
	 double calcularIR(int qtdDependentes, double salarioBruto);
	 double calcularSalarioLiquido(double salarioBruto, double descontoINSS, double descontoIR);
}  
