import model.Funcionario;
import service.ArquivoService;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArquivoService arquivoService = new ArquivoService();
        ArrayList<Funcionario> funcionarios = new ArrayList<>();

        funcionarios = arquivoService.carregarFuncionariosDeArquivo();


    }
}
