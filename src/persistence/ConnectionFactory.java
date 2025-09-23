package persistence;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;


public class ConnectionFactory {

<<<<<<< HEAD
	String url = "jdbc:postgresql://localhost:5432/curso";
	String usuario = "postgres";
	String senha = "root";
	private Connection connection;

	public Connection getConnection() {
		System.out.println("Conectando no banco de dados.....");
		
		try {
			connection = DriverManager.getConnection(url, usuario, senha);
			if (connection != null) {
				System.out.println("Conectado com sucesso!");
			} else {
				System.out.println("Erro nos dados da conexão!");
			}
		} catch (SQLException e) {
			System.err.println("Não foi possível conectar...");
		}
		
		return connection;
	}
=======
    String url = "jdbc:postgresql://localhost:5432/curso";
    String usuario = "postgres";
    String senha = "root";
    private Connection connection;

    public Connection getConnection() {
        System.out.println("Conectando no banco de dados.....");
>>>>>>> origin/fernando-henrique

        try {
            connection = DriverManager.getConnection(url, usuario, senha);
            if (connection != null) {
                System.out.println("Conectado com sucesso!");
            } else {
                System.out.println("Erro nos dados da conexão!");
            }
        } catch (SQLException e) {
            System.err.println("Não foi possível conectar...");
        }

        return connection;
    }

}