package prueba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.postgis.PGbox3d;
import org.postgis.PGgeometry;

public class Conexion {

	public static void main(String[] args) {

		String driver = "org.postgresql.Driver";
		String connectString = "jdbc:postgresql://localhost:5432/gsiamdb";
		String user = "postgres";
		String password = "postgres";

		try {
			
			Class.forName(driver);
			Connection con = DriverManager.getConnection(connectString, user,
					password);
			
			((org.postgresql.PGConnection)con).addDataType("geometry", PGgeometry.class);
			((org.postgresql.PGConnection)con).addDataType("box3d",PGbox3d.class);
			
			Statement stmt = con.createStatement();
		
			ResultSet rs = stmt
					.executeQuery("select the_geom as geom from americas_south_america_uruguay_poi");

			while (rs.next()) {
				PGgeometry geom = (PGgeometry) rs.getObject(1);
				System.out.println(geom.toString());
			}

			stmt.close();
			con.close();

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
}