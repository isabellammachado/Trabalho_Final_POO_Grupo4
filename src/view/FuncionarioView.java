package view;

import java.util.ArrayList;
import java.util.List;

import exception.DependenteException;
import model.Funcionario;
import model.ListagemFuncionarios;
import service.ArquivoService;

public class FuncionarioView {


    public void selecionarMenu() throws DependenteException {
        int opcao;
        List<Funcionario> funcionarios = new ArrayList<>();
        ListagemFuncionarios lista = new ListagemFuncionarios();


        do {
            View.menu();
            opcao = View.selecionarMenu();

            switch (opcao) {
                case 1:
                    lista.adicionarFuncionario();
                    break;
                case 2:
                    System.out.println("Alterar");
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
                    funcionarios = service.lerArquivo();
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
