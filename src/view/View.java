package view;
import resource.Util;

public class View {
	static final String HEADER = "SiSTEMA DE CADASTRO DE FUNCIONÁRIOS";
	static final String LINHA = "---------------------------------";

	public static void menu() {
		header();
		linha();
		System.out.println("""

				1 - Cadastrar um novo funcionário
				2 - Alterar dados de um funcionário existente
				3 - Excluir um funcionário
				4 - Imprimir informações de um funcionário pelo CPF
				5 - Listar todos os funcionários e seus dependentes
				6 - Cadastrar funcionários a partir de um arquivo .txt
				7 - Sair do sistema
				""");
		linha();
	}
	public static int selecionarMenu() {
		int opcao = Util.informarInt("Selecione uma opção: ");

		return opcao;
	}

	public static void header() {
		System.out.println(HEADER);

	}
	public static void linha() {
		System.out.println(LINHA);
	}
}
