package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import enums.Parentesco;
import exception.DependenteException;
import model.Dependente;
import model.FolhaPagamento;
import model.Funcionario;
import dao.FuncionarioDao;
import dao.DependenteDao;

public class ArquivoService {

    public ArrayList<Funcionario> carregarFuncionariosDeArquivo() {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        FuncionarioDao funcionarioDao = new FuncionarioDao();
        DependenteDao dependenteDao = new DependenteDao();

        Scanner sc = new Scanner(System.in);
        System.out.print("Informe o nome de um arquivo ou diretório: ");
        String arquivo = sc.nextLine();
        File file = new File(arquivo);

        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(";");

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine().trim();
                if (linha.isEmpty()) continue;

                String[] dados = linha.split(";");
                if (dados.length == 4) {
                    String nome = dados[0].trim();
                    String cpf = dados[1].trim();
                    LocalDate dataNascimento = LocalDate.parse(dados[2].trim(),
                            DateTimeFormatter.ofPattern("yyyyMMdd"));
                    double salarioBruto = Double.parseDouble(dados[3].trim().replace(',', '.'));

                    Funcionario funcionario = new Funcionario(nome, cpf, dataNascimento, salarioBruto);
                    funcionarioDao.inserir(funcionario); 

                    while (scanner.hasNextLine()) {
                        String linhaDependente = scanner.nextLine().trim();
                        if (linhaDependente.isEmpty()) break;

                        String[] dadosDep = linhaDependente.split(";");

                        String nomeDep = dadosDep[0].trim();
                        String cpfDep = dadosDep[1].trim();
                        LocalDate dataNascimentoDep = LocalDate.parse(dadosDep[2].trim(),
                                DateTimeFormatter.ofPattern("yyyyMMdd"));
                        Parentesco parentesco = Parentesco.valueOf(dadosDep[3].trim().toUpperCase());

                        Dependente dependente = new Dependente(nomeDep, cpfDep, dataNascimentoDep, parentesco);
                        funcionario.adicionarDependente(dependente);
                        dependenteDao.inserir(dependente, funcionario); 
                    }

                    funcionarios.add(funcionario);
                } else {
                    System.err.println("Formato de linha inválido: " + linha);
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + arquivo);
        } catch (DependenteException | IllegalArgumentException e) {
            System.err.println("Erro de validação: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro ao inserir no banco: " + e.getMessage());
        }

        System.out.println("Funcionários carregados e salvos no banco com sucesso!");
        return funcionarios;
    }

    public static void ExportarArquivoTXT(ArrayList<Funcionario> funcionarios, ArrayList<FolhaPagamento> folhaPagamentos) {
        try (PrintWriter writer = new PrintWriter("Teste.txt")) {
            for (FolhaPagamento fp : folhaPagamentos) {
                writer.println(fp.getFuncionario().getNome() + ";" +
                        fp.getFuncionario().getCpf() + ";" +
                        fp.getDataPagamento() + ";" +
                        String.format("%.2f", fp.getDescontoINSS()) + ";" +
                        String.format("%.2f", fp.getDescontoIR()) + ";" +
                        String.format("%.2f", fp.getSalarioLiquido()));
            }
            System.out.println("Folha de Pagamento criada!");
        } catch (Exception e) {
            System.err.println("Erro ao exportar arquivo: " + e.getMessage());
        }
    }
}
