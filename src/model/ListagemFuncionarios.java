package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import exception.DependenteException;
import dao.FuncionarioDao;
import enums.Parentesco;

public class ListagemFuncionarios {
	private ArrayList<Funcionario> funcionarios = new ArrayList<>();

	public ArrayList<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void adicionarFuncionario() throws DependenteException {
		Scanner sc = new Scanner(System.in);

        System.out.print("Código: ");
        int codigo = Integer.parseInt(sc.nextLine());

		System.out.print("Nome: ");
		String nome = sc.nextLine();

		System.out.print("CPF: ");
		String cpf = sc.nextLine();

		System.out.print("Data de Nascimento (AAAA-MM-DD): ");
		String dataNascimentoStr = sc.nextLine();
		
		LocalDate dataNascimento;
		try {
		    // tenta no formato padrão ISO (AAAA-MM-DD)
		    dataNascimento = LocalDate.parse(dataNascimentoStr);
		} catch (Exception e) {
		    // se der erro, tenta com barras (AAAA/MM/DD)
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		    dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
		}

		System.out.println("Salário Bruto: ");
		double salarioBruto = Double.parseDouble(sc.nextLine());

		System.out.print("Quantidade de dependentes: ");
		int qtdDependentes = Integer.parseInt(sc.nextLine());
		ArrayList<Dependente> dependentes = new ArrayList<>();

		for (int i = 0; i < qtdDependentes; i++) {
			System.out.print("Nome do dependente " + (i + 1) + ": ");
			String nomeDep = sc.nextLine();

			System.out.print("CPF: ");
			String cpfDep = sc.nextLine();

			System.out.print("Data de Nascimento do dependente " + (i + 1) + " (AAAA-MM-DD): ");
			String dataNascimentoDep = sc.nextLine();
			
			
			System.out.println("Escolha o parentesco:");
			for (Parentesco p : Parentesco.values()) {
				System.out.println(p.ordinal() + " - " + p.name());
			}
			int parentescoIdx = Integer.parseInt(sc.nextLine());
			Parentesco parentesco = Parentesco.values()[parentescoIdx];

			dependentes.add(new Dependente(nomeDep, cpfDep, dataNascimento, parentesco));
		}

		Funcionario funcionario = new Funcionario(codigo, nome, cpf, dataNascimento, salarioBruto);

		funcionarios.add(funcionario);
	 // Salva no banco de dados
		System.out.println("Funcionário adicionado com sucesso.");
	}

	public void alterarFuncionario() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Digite o CPF do funcionário a ser alterado: ");
		String cpfBusca = sc.nextLine();

		Funcionario funcionario = null;
		for (Funcionario f : funcionarios) {
			if (f.getCpf().equals(cpfBusca)) {
				funcionario = f;
				break;
			}
		}
		if (funcionario == null) {
			System.out.println("Funcionário não encontrado.");
			sc.close();
			return;
		}
		System.out.print("Novo nome (" + funcionario.getNome() + "): ");
		String novoNome = sc.nextLine();
		if (!novoNome.isEmpty()) {
			funcionario.setNome(novoNome);
		}
		System.out.print("Novo salário bruto (" + funcionario.getSalarioBruto() + "): ");
		String novoSalario = sc.nextLine();
		if (!novoSalario.isEmpty()) {
			funcionario.setSalarioBruto(Double.parseDouble(novoSalario));
		}

		System.out.println("Funcionário alterado com sucesso.");
		sc.close();
	}

	public void carregarFuncionariosDeArquivo(String caminho) {

		// Implementar a leitura do arquivo txt e adicionar os funcionários à lista
		//
		// Funcionario func = new Funcionario(...);
		// adicionarFuncionario(func);

	}

	// método para ler do arquivo txt

}
