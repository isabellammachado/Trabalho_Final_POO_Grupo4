package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import dao.DependenteDao;
import dao.FuncionarioDao;
import enums.Parentesco;
import exception.DependenteException;

public class ListagemFuncionarios {

    private FuncionarioDao funcionarioDao;
    private DependenteDao dependenteDao;

    public ListagemFuncionarios() {
        this.funcionarioDao = new FuncionarioDao();
        this.dependenteDao = new DependenteDao();
    }

    public void adicionarFuncionario() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Nome do funcionário: ");
            String nome = sc.nextLine();

            System.out.print("CPF: ");
            String cpf = sc.nextLine();

            System.out.print("Data de nascimento (yyyy-MM-dd): ");
            String dataNascimentoStr = sc.nextLine();
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr);

            System.out.print("Salário bruto: ");
            double salarioBruto = Double.parseDouble(sc.nextLine());

            Funcionario funcionario = new Funcionario(nome, cpf, dataNascimento, salarioBruto);
            funcionarioDao.inserir(funcionario);

            System.out.println("Deseja adicionar dependentes? (S/N)");
            String resp = sc.nextLine().trim().toUpperCase();
            while (resp.equals("S")) {
                System.out.print("Nome do dependente: ");
                String nomeDep = sc.nextLine();

                System.out.print("CPF do dependente: ");
                String cpfDep = sc.nextLine();

                System.out.print("Data de nascimento (yyyy-MM-dd): ");
                LocalDate dataNascimentoDep = LocalDate.parse(sc.nextLine());

                System.out.print("Parentesco (FILHO, FILHA, OUTROS): ");
                Parentesco parentesco = Parentesco.valueOf(sc.nextLine().toUpperCase());

                Dependente dependente = new Dependente(nomeDep, cpfDep, dataNascimentoDep, parentesco);
                dependenteDao.inserir(dependente, funcionario);
                funcionario.adicionarDependente(dependente);

                System.out.println("Deseja adicionar outro dependente? (S/N)");
                resp = sc.nextLine().trim().toUpperCase();
            }

            System.out.println("Funcionário cadastrado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao adicionar funcionário: " + e.getMessage());
        }
    }

    public void alterarFuncionario() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o CPF do funcionário a ser alterado: ");
        String cpfBusca = sc.nextLine();

        try {
            List<Funcionario> funcionarios = funcionarioDao.listar();
            Funcionario funcionario = null;

            for (Funcionario f : funcionarios) {
                if (f.getCpf().equals(cpfBusca)) {
                    funcionario = f;
                    break;
                }
            }

            if (funcionario == null) {
                System.out.println("Funcionário não encontrado.");
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

            funcionarioDao.atualizar(funcionario);
            System.out.println("Funcionário alterado com sucesso no banco.");

        } catch (Exception e) {
            System.out.println("Erro ao alterar funcionário: " + e.getMessage());
        }
    }

    public void removerFuncionario() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o CPF do funcionário a ser removido: ");
        String cpfBusca = sc.nextLine();

        try {
            List<Funcionario> funcionarios = funcionarioDao.listar();
            Funcionario funcionario = null;

            for (Funcionario f : funcionarios) {
                if (f.getCpf().equals(cpfBusca)) {
                    funcionario = f;
                    break;
                }
            }

            if (funcionario == null) {
                System.out.println("Funcionário não encontrado.");
                return;
            }

            funcionarioDao.remover(funcionario.getCodigo());
            System.out.println("Funcionário removido com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao remover funcionário: " + e.getMessage());
        }
    }

    public void imprimirFuncionarioCpf(List<Funcionario> funcionarios) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o CPF do funcionário: ");
        String cpf = sc.nextLine();

        for (Funcionario f : funcionarios) {
            if (f.getCpf().equals(cpf)) {
                System.out.println(f);
                if (!f.getDependentes().isEmpty()) {
                    System.out.println("Dependentes:");
                    for (var d : f.getDependentes()) {
                        System.out.println("  - " + d);
                    }
                }
                return;
            }
        }

        System.out.println("Funcionário não encontrado.");
    }

    public void exibirFuncionarios(List<Funcionario> funcionarios) {
        for (Funcionario f : funcionarios) {
            System.out.println(f);
            if (!f.getDependentes().isEmpty()) {
                System.out.println("Dependentes:");
                for (var d : f.getDependentes()) {
                    System.out.println("  - " + d);
                }
            }
        }
    }
}
