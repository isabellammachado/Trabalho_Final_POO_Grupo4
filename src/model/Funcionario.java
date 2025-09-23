package model;

import java.time.LocalDate;
import java.util.ArrayList;
import exception.DependenteException;


public class Funcionario extends Pessoa {
    private int codigo;
	private double salarioBruto;
	private double descontoINSS;
	private double descontoIR;
	private ArrayList<Dependente> dependentes;

	public Funcionario(String nome, String cpf, LocalDate dataNascimento, double salarioBruto) {
		super(nome, cpf, dataNascimento);
        this.codigo = -1;
		this.salarioBruto = salarioBruto;
		this.dependentes = new ArrayList<>();
		this.descontoINSS = 0.0;
		this.descontoIR = 0.0;
	}

	public double getSalarioBruto() {
		return salarioBruto;
	}

	public void setSalarioBruto(double salarioBruto) {
		this.salarioBruto = salarioBruto;
	}

	public double getDescontoINSS() {
		return descontoINSS;
	}

	public void setDescontoINSS(double descontoINSS) {
		this.descontoINSS = descontoINSS;
	}

	public double getDescontoIR() {
		return descontoIR;
	}

	public void setDescontoIR(double descontoIR) {
		this.descontoIR = descontoIR;
	}

	public ArrayList<Dependente> getDependentes() {
		return dependentes;
	}

	public void adicionarDependente(Dependente dependente) throws DependenteException {
		for (Dependente dep : dependentes) {
			if (dep.getCpf().equals(dependente.getCpf())) {
				throw new DependenteException("Dependente com este CPF já existe!");
			}
		}
		dependentes.add(dependente);
	}

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + " | CPF: " + cpf + " | Data Nascimento: "
                + dataNascimento + " | Salário Bruto: " + salarioBruto;
    }
}
