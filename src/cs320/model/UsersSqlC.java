package cs320.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cs320.pattern.FactorySqlF;

public class UsersSqlC implements UsersC {
	
	public boolean valid(String usr, String pwd) {
		boolean ret = false;
		
		try {
			Connection conn = FactorySqlF.getDbConnection();
			String sql = "select 1 from users where user_name = ? and password = AES_ENCRYPT(?,?)";
			PreparedStatement pstmt = conn.prepareStatement( sql );
			pstmt.setString( 1, usr );
			pstmt.setString( 2, pwd );
			pstmt.setString( 3, pwd );
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				ret = true;
			}
			conn.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
        
        return ret;
	}
	
	public UserD getUserByName(String name) {
		UserD usr = null;
		
		try {
			Connection conn = FactorySqlF.getDbConnection();
			String sql = "select id, user_name from users where user_name = ?";
	        PreparedStatement pstmt = conn.prepareStatement( sql );
	        pstmt.setString( 1, name );
	        ResultSet rs = pstmt.executeQuery();
	        if(rs.next()) {
	        	usr = new UserD(rs.getInt("id"), rs.getString("user_name"));
	        }
	        conn.close();
	        
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		      
		return usr;
	}

	public UserD getUserByID(int id) throws SQLException {
		UserD usr = null;
		Connection conn = FactorySqlF.getDbConnection();
		
		String sql = "select id, user_name from users where id = ?";
        PreparedStatement pstmt = conn.prepareStatement( sql );
        pstmt.setInt( 1, id );
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
        	usr = new UserD(rs.getInt("id"), rs.getString("user_name"));
        }
        
        conn.close();
        
		return usr;
	}
}
