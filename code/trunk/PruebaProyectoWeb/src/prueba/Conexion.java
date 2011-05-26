package prueba;

import java.sql.*;


public class Conexion{
	
public static void main(String[] args) {
	
	
String driver = "org.postgresql.Driver";
String connectString = "jdbc:postgresql://localhost:5432/prueba";
String user = "postgres";
String password = "postgres";

try{
	System.out.println("el Tony se la mastica - ");
Class.forName(driver);
Connection con = DriverManager.getConnection(connectString, user , password);
Statement stmt = con.createStatement();

ResultSet rs = stmt.executeQuery("select * from americas_south_america_uruguay_poi");

while (rs.next()){
System.out.println("lalala" + rs.getString("category"));
}

stmt.close();
con.close();

}

catch ( Exception e ){
e.printStackTrace();
}
}
}