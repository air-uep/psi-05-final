package pl.air;

import pl.air.db.DatabaseService;

import java.sql.SQLException;

public class Main {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		String user = "...";
		String password = "...";
		String host = "...";
		String database = "...";
		DatabaseService srv = new DatabaseService(user, password, host, database);
		srv.getEmployees();
		srv.shutDown();
	}

}
