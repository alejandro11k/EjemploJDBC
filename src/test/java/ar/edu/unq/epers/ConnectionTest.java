package ar.edu.unq.epers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import junit.framework.Assert;

import org.junit.Test;

public class ConnectionTest{
	
	@Test
	public void testInsert() throws Exception  {		
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = this.getConnection();
			ps = conn.prepareStatement("INSERT INTO Aerolinea (NOMBRE, CODIGO) VALUES (?,?)");
			ps.setString(1, "UnaAerolinea");
			ps.setString(2, "UNA");
			ps.execute();
			
			Assert.assertEquals("Se espera que haya podido insertar 1 registro",1, ps.getUpdateCount());
			ps.close();
		}finally{
			if(ps != null)
				ps.close();
			if(conn != null)
				conn.close();
		}
	}

	@Test
	public void testSelect() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = this.getConnection();
			ps = conn.prepareStatement("SELECT ID,NOMBRE,CODIGO FROM Aerolinea WHERE CODIGO = ?");
			ps.setString(1, "UNA");
			ResultSet rs = ps.executeQuery();

			while(rs.next()){
				Integer id = rs.getInt("ID");
				String nombre = rs.getString("NOMBRE");
				String codigo = rs.getString("CODIGO");
				
				System.out.println("ID:" + id + " nombre:" + nombre + " codigo:" + codigo);
			}
			
			ps.close();
		}finally{
			if(ps != null)
				ps.close();
			if(conn != null)
				conn.close();
		}
		
	}
	
	private Connection getConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost/Epers_Ej1?user=root&password=root");
	}	
}
