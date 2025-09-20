package persistence;

import java.sql.Connection;

public class TestaConnection {
	public static void main(String[] args) {
		Connection connection = new ConnectionFactory().getConnection();
	}
	
}
