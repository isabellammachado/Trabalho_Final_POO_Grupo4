package service;

import model.Dependente;
import model.Funcionario;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exception.DependenteException;
import enums.Parentesco;

public class ArquivoService {
	public List<Funcionario> lerArquivo() throws DependenteException {
	    Scanner input = new Scanner(System.in);
	    System.out.println("---Digite o caminho do arquivo---");
	    System.out.println("Seguindo este exemplo: C:\\Users\\User\\Desktop\\Temp\\texto.txt");
	    System.out.print("Caminho: ");
	    String caminhoArquivo = input.nextLine(); 

	    List<Funcionario> funcionarios = new ArrayList<>();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	    try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
	        String linha;
	        Funcionario atual = null;

	        while ((linha = br.readLine()) != null) {
	            linha = linha.trim();
	            if (linha.isEmpty()) {
	                atual = null; 
	                continue;
	            }

	            String[] partes = linha.split(";");
	            if (partes.length < 4) continue;

	            if (isNumeric(partes[3])) { 
	                String nome = partes[0];
	                String cpf = partes[1];
	                LocalDate nascimento = LocalDate.parse(partes[2], formatter);
	                double salario = Double.parseDouble(partes[3]);

	                atual = new Funcionario(nome, cpf, nascimento, salario);
	                funcionarios.add(atual);
	            } else { 
	                if (atual != null) {
	                    String nome = partes[0];
	                    String cpf = partes[1];
	                    LocalDate dataNascimento = LocalDate.parse(partes[2], formatter);

	                    Parentesco parentescoEnum;
	                    try {
	                        parentescoEnum = Parentesco.valueOf(partes[3].toUpperCase());
	                    } catch (IllegalArgumentException e) {
	                        parentescoEnum = Parentesco.OUTROS;
	                    }

	                    Dependente dep = new Dependente(nome, cpf, dataNascimento, parentescoEnum);
	                    atual.adicionarDependente(dep);
	                }
	            }
	        }

	    } catch (IOException e) {
	        System.out.println("Erro ao ler o arquivo: " + e.getMessage());
	        e.printStackTrace();
	    }
	    System.out.println("---Arquivo cadastrado com sucesso---\n\n");
	    return funcionarios;
	   
	}

	private boolean isNumeric(String str) {
	    if (str == null) return false;
	    try {
	        Double.parseDouble(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
}