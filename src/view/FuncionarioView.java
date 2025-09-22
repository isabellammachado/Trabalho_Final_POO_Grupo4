package view;

import exception.DependenteException;
import model.ListagemFuncionarios;

public class FuncionarioView {

public void selecionarMenu() throws DependenteException {
    int opcao;
    do {
        View.menu();
        opcao = View.selecionarMenu();

        ListagemFuncionarios lista = new ListagemFuncionarios(); // ISSO DEVE FICAR FORA DO LAÇO

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
                System.out.println("Imprimir um funcionário");
                break;
            case 5:
                System.out.println("Listar Funcionários");
                break;
            case 6:
                System.out.println("Sair");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    } while (opcao != 6);
}

}
