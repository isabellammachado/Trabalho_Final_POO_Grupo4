package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exception.DependenteException;
import model.Funcionario;
import model.ListagemFuncionarios;
import service.ArquivoService;

public class FuncionarioView {

    private List<Funcionario> funcionarios;
    private ListagemFuncionarios lista;

    public FuncionarioView(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
        this.lista = new ListagemFuncionarios();
    }

    public FuncionarioView() {
        this.funcionarios = new ArrayList<>();
        this.lista = new ListagemFuncionarios();
    }

    public void selecionarMenu() throws DependenteException {
        int opcao;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("\nSiSTEMA DE CADASTRO DE FUNCIONÁRIOS");
            System.out.println("---------------------------------");
            System.out.println("1 - Cadastrar um novo funcionário");
            System.out.println("2 - Alterar dados de um funcionário existente");
            System.out.println("3 - Excluir um funcionário");
            System.out.println("4 - Imprimir informações de um funcionário pelo CPF");
            System.out.println("5 - Listar todos os funcionários e seus dependentes");
            System.out.println("6 - Cadastrar funcionários a partir de um arquivo .txt");
            System.out.println("7 - Sair do sistema");
            System.out.println("---------------------------------");
            System.out.print("Selecione uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine(); // consumir quebra de linha

            switch (opcao) {
                case 1:
                    lista.adicionarFuncionario();
                    break;
                case 2:
                    lista.alterarFuncionario();
                    break;
                case 3:
                    System.out.println("Excluir");
                    break;
                case 4:
                    lista.imprimirFuncionarioCpf(funcionarios);
                    break;
                case 5:
                    lista.exibirFuncionarios(funcionarios);
                    break;
                case 6:
                    ArquivoService service = new ArquivoService();
                    funcionarios = service.carregarFuncionariosDeArquivo();
                    break;
                case 7:
                    System.out.println("Sair");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 7);
    }
}
