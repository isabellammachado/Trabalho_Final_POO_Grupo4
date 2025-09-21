package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ArquivoService {
	
	public static void leituraArquivoCSV() {
		Scanner sc = new Scanner(System.in);
		System.out.printf("Informe o nome de um arquivo ou diretório: ");

		String nome = sc.nextLine();
		File file = new File(nome);
		try {
			Scanner ler = new Scanner(file);
			ler.useDelimiter(";");

			while (ler.hasNext()) {
				System.out.println(ler.nextLine());
			}
			ler.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado !");
			e.printStackTrace();
		}
	}
}
