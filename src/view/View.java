package view;
import resource.Util;

public class View {
	static final String HEADER = "SiSTEMA DE CADASTRO DE FUNCIONÁRIOS";
	static final String LINHA = "---------------------------------";
	
	public static void menu() {
		header();
		linha();
		System.out.println("""
				1 - Cadastrar 
				2 - Alterar 
				3 - Excluir
				4 - Imprimir um funcionário
				5 - Listar Funcionários
				6 - Sair
				""");
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
