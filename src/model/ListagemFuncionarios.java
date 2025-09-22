package model;

import service.ArquivoService;
import model.Funcionario;
import exception.DependenteException;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import view.View;
import dao.FuncionarioDao;
import enums.Parentesco;

public class ListagemFuncionarios {
	private ArrayList<Funcionario> funcionarios = new ArrayList<>();

	public ArrayList<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	
	private boolean validarCPF(String cpf) {
	    if (cpf == null) return false;
	    return cpf.matches("\\d{11}");
	}


	public void adicionarFuncionario() throws DependenteException {
	    boolean cancelarCadastro = false;

	    do {
	        Scanner sc = new Scanner(System.in);

	        try {
                System.out.print("Nome: ");
                int codigo = sc.nextInt();
                if (codigo <= 0) {
                    System.out.println("Código inválido. Cadastro cancelado.");
                    break;
                }

	            System.out.print("Nome: ");
	            String nome = sc.nextLine();
	            if (nome == null || nome.trim().isEmpty()) {
	                System.out.println("Nome inválido. Cadastro cancelado.");
	                break;
	            }

	            System.out.print("CPF: ");
	            String cpf = sc.nextLine();
	            if (!validarCPF(cpf)) {
	                System.out.println("CPF inválido. Cadastro cancelado.");
	                break;
	            }

	            System.out.print("Data de Nascimento (AAAA-MM-DD): ");
	            String dataNascimentoStr = sc.nextLine();
	            LocalDate dataNascimento;
	            try {
	                dataNascimento = LocalDate.parse(dataNascimentoStr);
	            } catch (Exception e) {
	                System.out.println("Data de nascimento inválida. Cadastro cancelado.");
	                break;
	            }

	            System.out.print("Salário Bruto: ");
	            double salarioBruto;
	            try {
	                salarioBruto = Double.parseDouble(sc.nextLine());
	            } catch (Exception e) {
	                System.out.println("Salário inválido. Cadastro cancelado.");
	                break;
	            }

	            System.out.print("Quantidade de dependentes: ");
	            int qtdDependentes;
	            try {
	                qtdDependentes = Integer.parseInt(sc.nextLine());
	            } catch (Exception e) {
	                System.out.println("Quantidade inválida. Cadastro cancelado.");
	                break;
	            }

	            ArrayList<Dependente> dependentes = new ArrayList<>();

	            for (int i = 0; i < qtdDependentes; i++) {
                    System.out.print("Nome do dependente " + (i + 1) + ": ");
	                String nomeDep = sc.nextLine();
	                if (nomeDep == null || nomeDep.trim().isEmpty()) {
	                    System.out.println("Nome do dependente inválido. Cadastro cancelado.");
	                    cancelarCadastro = true;
	                    break;
	                }

	                System.out.print("CPF do dependente: ");
	                String cpfDep = sc.nextLine();
	                if (!validarCPF(cpfDep)) {
	                    System.out.println("CPF do dependente inválido. Cadastro cancelado.");
	                    cancelarCadastro = true;
	                    break;
	                }

	                System.out.print("Data de Nascimento do dependente " + (i + 1) + " (AAAA-MM-DD): ");
	                String dataNascimentoDep = sc.nextLine();
	                LocalDate dataDep;
	                try {
	                    dataDep = LocalDate.parse(dataNascimentoDep);
	                } catch (Exception e) {
	                    System.out.println("Data de nascimento do dependente inválida. Cadastro cancelado.");
	                    cancelarCadastro = true;
	                    break;
	                }

	                System.out.println("Escolha o parentesco:");
	                for (Parentesco p : Parentesco.values()) {
	                    System.out.println(p.ordinal() + " - " + p.name());
	                }

	                int parentescoIdx;
	                try {
	                    parentescoIdx = Integer.parseInt(sc.nextLine());
	                    if (parentescoIdx < 0 || parentescoIdx >= Parentesco.values().length) {
	                        System.out.println("Parentesco inválido. Cadastro cancelado.");
	                        cancelarCadastro = true;
	                        break;
	                    }
	                } catch (Exception e) {
	                    System.out.println("Parentesco inválido. Cadastro cancelado.");
	                    cancelarCadastro = true;
	                    break;
	                }

	                Parentesco parentesco = Parentesco.values()[parentescoIdx];
	                dependentes.add(new Dependente(nomeDep, cpfDep, dataDep, parentesco));
	            }

	            if (cancelarCadastro) break;

	            Funcionario funcionario = new Funcionario(codigo, nome, cpf, dataNascimento, salarioBruto);
	            funcionarios.add(funcionario);

	            System.out.println("Funcionário adicionado com sucesso.");
	            cancelarCadastro = true; 

	        } catch (Exception e) {
	            System.out.println("Erro inesperado. Cadastro cancelado.");
	            cancelarCadastro = true;
	        }

	    } while (!cancelarCadastro);
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

	public void carregarFuncionariosDeArquivo() {
	        ArquivoService service = new ArquivoService();  

	        try {
	            List<Funcionario> funcionarios = service.carregarFuncionariosDeArquivo();

	            for (Funcionario f : funcionarios) {
	                System.out.println("Funcionário: " + f.getNome());
	            }
	        } catch (Exception e) {
	            System.out.println("Erro: " + e.getMessage());
	        }
	}
	
	public void exibirFuncionarios(List<Funcionario> funcionarios) {
	    System.out.println("\n===== Lista de Funcionários =====\n");

	    if (funcionarios.isEmpty()) {
	        System.out.println("Nenhum funcionário cadastrado.");
	        return;
	    }

	    for (Funcionario f : funcionarios) {
	        System.out.println("Nome: " + f.getNome());
	        System.out.println("CPF: " + f.getCpf());
	        System.out.println("Data de Nascimento: " + f.getDataNascimento());
	        System.out.println("Salário: R$ " + String.format("%.2f", f.getSalarioBruto()));

	        if (!f.getDependentes().isEmpty()) {
	            System.out.println("Dependentes:");
	            for (Dependente d : f.getDependentes()) {
	                System.out.println("  • Nome: " + d.getNome());
	                System.out.println("    Parentesco: " + d.getParentesco());
	                System.out.println("    Data de Nascimento: " + d.getDataNascimento());
	            }
	        } else {
	            System.out.println("Dependentes: Nenhum");
	        }

	        System.out.println("\n----------------------------------------\n");
	    }
	}

	public void imprimirFuncionarioCpf(List<Funcionario> funcionarios) {
	    Scanner input = new Scanner(System.in);
	    System.out.print("Digite o CPF do funcionário: ");
	    String cpfProcurado = input.nextLine();

	    boolean encontrado = false;

	    for (Funcionario f : funcionarios) {
	        if (f.getCpf().equals(cpfProcurado)) {
	            System.out.println("\n===== Funcionário Encontrado =====\n");
	            System.out.println("Nome: " + f.getNome());
	            System.out.println("CPF: " + f.getCpf());
	            System.out.println("Data de Nascimento: " + f.getDataNascimento());
	            System.out.println("Salário: R$ " + String.format("%.2f", f.getSalarioBruto()));

	            if (!f.getDependentes().isEmpty()) {
	                System.out.println("Dependentes:");
	                for (Dependente d : f.getDependentes()) {
	                    System.out.println("  • Nome: " + d.getNome());
	                    System.out.println("    Parentesco: " + d.getParentesco());
	                    System.out.println("    Data de Nascimento: " + d.getDataNascimento());
	                }
	            } else {
	                System.out.println("Dependentes: Nenhum");
	            }

	            System.out.println("\n----------------------------------------\n");
	            encontrado = true;
	            break; // sai do loop depois de achar
	        }
	    }

	    if (!encontrado) {
	        System.out.println("Funcionário não encontrado ou sem registro.");
	    }
	}

}
