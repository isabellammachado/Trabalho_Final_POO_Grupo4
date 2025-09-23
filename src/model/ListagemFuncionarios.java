package model;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import dao.DependenteDao;
import dao.FuncionarioDao;
import enums.Parentesco;
import exception.DependenteException;
import persistence.ConnectionFactory;

public class ListagemFuncionarios {

    private FuncionarioDao funcionarioDao;
    private DependenteDao dependenteDao;

    public ListagemFuncionarios() {
        this.funcionarioDao = new FuncionarioDao();
        this.dependenteDao = new DependenteDao();
    }

    // Adiciona um funcionário e seus dependentes
    public void adicionarFuncionario() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Nome do funcionário: ");
            String nome = sc.nextLine();

            System.out.print("CPF: ");
            String cpf = sc.nextLine();

            System.out.print("Data de nascimento (yyyy-MM-dd): ");
            LocalDate dataNascimento = LocalDate.parse(sc.nextLine());

            System.out.print("Salário bruto: ");
            double salarioBruto = Double.parseDouble(sc.nextLine());

            Funcionario funcionario = new Funcionario(nome, cpf, dataNascimento, salarioBruto);
            funcionarioDao.inserir(funcionario);

            String resp;
            do {
                System.out.println("Deseja adicionar dependentes? (S/N)");
                resp = sc.nextLine().trim().toUpperCase();
                if (resp.equals("S")) {
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
                }
            } while (resp.equals("S"));

            System.out.println("Funcionário cadastrado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao adicionar funcionário: " + e.getMessage());
        }
    }

    // Altera os dados de um funcionário
    public void alterarFuncionario() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o CPF do funcionário a ser alterado: ");
        String cpfBusca = sc.nextLine();

        try {
            List<Funcionario> funcionarios = funcionarioDao.listar();
            Funcionario funcionario = null;

            // Procura o funcionário pelo CPF
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
            System.out.println("Funcionário alterado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao alterar funcionário: " + e.getMessage());
        }
    }

    // Imprime informações de um funcionário pelo CPF
    public void imprimirFuncionarioCpf(List<Funcionario> funcionarios) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o CPF do funcionário: ");
        String cpf = sc.nextLine();

        boolean encontrado = false;
        for (Funcionario f : funcionarios) {
            if (f.getCpf().equals(cpf)) {
                System.out.println(f);
                if (!f.getDependentes().isEmpty()) {
                    System.out.println("Dependentes:");
                    for (Dependente d : f.getDependentes()) {
                        System.out.println("  - " + d);
                    }
                }
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Funcionário não encontrado.");
        }
    }

    // Exibe todos os funcionários e seus dependentes
    public void exibirFuncionarios(List<Funcionario> funcionarios) {
        for (Funcionario f : funcionarios) {
            System.out.println(f);
            if (!f.getDependentes().isEmpty()) {
                System.out.println("Dependentes:");
                for (Dependente d : f.getDependentes()) {
                    System.out.println("  - " + d);
                }
            }
        }
    }

    // Exporta a folha de pagamento para arquivo TXT
    public static void ExportarArquivoTXT() {
        try (Connection conn = new ConnectionFactory().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT f.nome, f.cpf, fp.dataPagamento, fp.descontoINSS, fp.descontoIR, fp.salarioLiquido " +
                     "FROM trabalho_final_poo.folha_pagamento fp " +
                     "JOIN trabalho_final_poo.funcionario f ON fp.funcionario_id = f.id")) {

            try (PrintWriter writer = new PrintWriter("Teste.txt")) {
                boolean temDados = false;
                while (rs.next()) {
                    temDados = true;
                    writer.println(
                            rs.getString("nome") + ";" +
                            rs.getString("cpf") + ";" +
                            rs.getDate("dataPagamento").toLocalDate() + ";" +
                            String.format("%.2f", rs.getDouble("descontoINSS")) + ";" +
                            String.format("%.2f", rs.getDouble("descontoIR")) + ";" +
                            String.format("%.2f", rs.getDouble("salarioLiquido"))
                    );
                }
                if (temDados) {
                    System.out.println("Folha de Pagamento criada com sucesso!");
                } else {
                    System.out.println("Não há dados de folha de pagamento para exportar.");
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao exportar arquivo: " + e.getMessage());
        }
    }
}
