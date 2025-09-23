package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exception.DependenteException;
import model.FolhaPagamento;
import model.Funcionario;
import model.ListagemFuncionarios;
import service.ArquivoService;
import service.FolhaPagamentoService;

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
            System.out.println("\nSISTEMA DE CADASTRO DE FUNCIONÁRIOS");
            System.out.println("---------------------------------");
            System.out.println("1 - Cadastrar um novo funcionário");
            System.out.println("2 - Alterar dados de um funcionário existente");
            System.out.println("3 - Imprimir informações de um funcionário pelo CPF");
            System.out.println("4 - Listar todos os funcionários e seus dependentes");
            System.out.println("5 - Cadastrar funcionários a partir de um arquivo .txt");
            System.out.println("6 - Exportar folha de pagamento para arquivo .txt");
            System.out.println("7 - Sair do sistema");
            System.out.println("---------------------------------");
            System.out.print("Selecione uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1 -> lista.adicionarFuncionario();               // Cadastrar novo funcionário
                case 2 -> lista.alterarFuncionario();                 // Alterar funcionário existente
                case 3 -> lista.imprimirFuncionarioCpf(funcionarios); // Imprimir pelo CPF
                case 4 -> lista.exibirFuncionarios(funcionarios);     // Listar todos
                case 5 -> {                                           // Carregar do arquivo .txt
                    ArquivoService service = new ArquivoService();
                    funcionarios = service.carregarFuncionariosDeArquivo();
                }
                case 6 -> {                                           // Gerar e exportar folha de pagamento
                    FolhaPagamentoService fps = new FolhaPagamentoService();
                    fps.gerarFolhaPagamento();
                    ListagemFuncionarios.ExportarArquivoTXT();
                }
                case 7 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 7);
    }
}
