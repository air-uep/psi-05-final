package pl.air.db;

import java.math.BigDecimal;
import java.sql.*;

public class DatabaseService {
	
	private Connection con;
	
	public DatabaseService(String user, String password, String host, String database) throws ClassNotFoundException, SQLException {
		Class.forName(com.mysql.cj.jdbc.Driver.class.getName());
		
		String url = String.format("jdbc:mysql://" + host + "/" + database);
		con = DriverManager.getConnection(url, user, password);
	}
	
	public void shutDown() throws SQLException {
		con.close();
	}
	
	public void getEmployees() throws /*ClassNotFoundException,*/ SQLException {
		System.out.println(" id | imię i nazwisko      | pensja + dodatek    | data zatr. | dział     ");
		System.out.println("---------------------------------------------------------------------------");
		
		Statement stmt = con.createStatement();
		String sql = "select * from employees";
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			int empId = rs.getInt("emp_id");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			
			BigDecimal salary = rs.getBigDecimal("salary");
			BigDecimal allowance = rs.getBigDecimal("allowance");
			
			Date hireDate = rs.getDate("hire_date");
			
			Integer depId = rs.getInt("dep_id");
			String depName = null;
			if (rs.wasNull()) {
				depId = null;
			}
			else {
				depName = getDepartmentName(depId);
			}
			
			//System.out.println(empId + " - " + firstName + " " + lastName + " - salary: " + salary + " + " + allowance + " - hire date: " + hireDate);
			System.out.format(
					"%d | %-20s | %9.2f + %7.2f | %tF | %d - %s \n",
					empId, firstName + " " + lastName, salary, allowance, hireDate, depId, depName
			);
		}
		
		rs.close();
		stmt.close();
	}

	public String getDepartmentName(Integer depId) throws SQLException {
		if (depId == null)
			return null;
		
		Statement stmt = con.createStatement();
		String sql = "select * from departments where dep_id = " + depId;
		ResultSet rs = stmt.executeQuery(sql);
		
		String name = null;
		if (rs.next()) {
			name = rs.getString("name");
		}
		
		rs.close();
		stmt.close();
		
		return name;
	}

}
