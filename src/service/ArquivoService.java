package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import enums.Parentesco;
import exception.DependenteException;
import model.Dependente;
import model.FolhaPagamento;
import model.Funcionario;

public class ArquivoService {

	public ArrayList<Funcionario> carregarFuncionariosDeArquivo() {
		ArrayList<Funcionario> funcionarios = new ArrayList<>();

		Scanner sc = new Scanner(System.in);
		System.out.print("Informe o nome de um arquivo ou diretório: ");

		String arquivo = sc.nextLine(); // Recebe o nome ou caminho do arqv
		File file = new File(arquivo); // Popula uma variavel com esse arquivo

		try {
			Scanner scanner = new Scanner(file); // Usa o arqv informado pelo usuario
			scanner.useDelimiter(";");

			while (scanner.hasNextLine()) {
				String linha = scanner.nextLine().trim(); // Corta a linha quando identf um ;

				// Faz a checagem de linha vazia para passar pro prox funcionario
				if (linha.isEmpty()) {
					continue;
				}
				String[] dados = linha.split(";"); // Separa os atributos da linha a cada ;

				if (dados.length == 4) {
					String nome = dados[0].trim();
					String cpf = dados[1].trim();
					LocalDate dataNascimento = LocalDate.parse(dados[2].trim(),
							DateTimeFormatter.ofPattern("yyyyMMdd"));
					double salarioBruto = Double.parseDouble(dados[3].trim().replace(',', '.'));

					// VERIFICA SOMENTE NO CSV. IDEAL COLOCAR CHECAGEM NO BANCO.
					for (Funcionario funcionario : funcionarios) {
						if (funcionario.getCpf().equals(cpf)) {
							throw new IllegalArgumentException("Funcionário com CPF repetido.");
						}
					}

					Funcionario funcionario = new Funcionario(nome, cpf, dataNascimento, salarioBruto); // Vai criar um
																										// obj
																										// funcionario
																										// com as
																										// informacoes
																										// da lista

					while (scanner.hasNextLine()) {
						String linhaDependente = scanner.nextLine().trim();
						if (linhaDependente.isEmpty()) {
							break; // Fim dos dependentes desse funcionario
						}

						String[] dadosDep = linhaDependente.split(";");

						String nomeDep = dadosDep[0].trim();
						String cpfDep = dadosDep[1].trim();
						LocalDate dataNascimentoDep = LocalDate.parse(dadosDep[2].trim(),
								DateTimeFormatter.ofPattern("yyyyMMdd"));
						Parentesco parentesco = Parentesco.valueOf(dadosDep[3].trim().toUpperCase());

						for (Dependente depExistente : funcionario.getDependentes()) {
							if (depExistente.getCpf().equals(cpfDep)) {
								throw new DependenteException("Dependente com CPF repetido: " + cpfDep);
							}
						}

						Dependente dependente = new Dependente(nomeDep, cpfDep, dataNascimentoDep, parentesco);
						funcionario.adicionarDependente(dependente);
					}

					funcionarios.add(funcionario);

				} else {
					// Tratar formato inválido ou pular linhas que não se encaixam - valida a linha
					System.err.println("Formato de linha inválido: " + linha);
				}
			}
			

		} catch (FileNotFoundException e) {
			System.err.println("Arquivo não encontrado no caminho: " + arquivo);
		} catch (DependenteException | IllegalArgumentException e) {
			System.err.println("Erro de validação ao carregar o arquivo: " + e.getMessage());
		}
        System.out.println("Funcionários carregados com sucesso!");
		return funcionarios;
	}

	public static void ExportarArquivoTXT(ArrayList<Funcionario> funcionarios, ArrayList<FolhaPagamento> folhaPagamentos) {
		try (PrintWriter writer = new PrintWriter(new FileWriter("Teste.txt"))) {
			
			for (FolhaPagamento fp : folhaPagamentos) {
				writer.println(fp.getFuncionario().getNome() + ";" + fp.getFuncionario().getCpf() + ";" + fp.getDataPagamento() + ";" + String.format("%.2f", fp.getDescontoINSS()) + ";" + String.format("%.2f", fp.getDescontoIR()) + ";" + String.format("%.2f", fp.getSalarioLiquido()) );
			}
			System.out.println("Folha de Pagamento criada!");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
