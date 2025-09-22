package main;

import exception.DependenteException;
import view.FuncionarioView;


public class Application {
	 public static void main(String[] args) throws DependenteException {
		 
	        FuncionarioView view = new FuncionarioView();
	        view.selecionarMenu();
	}

		
	}


