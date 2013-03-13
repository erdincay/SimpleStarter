package cs320.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class Users extends HashSet<User> {

	private static final long serialVersionUID = 1L;

	public static final String fstrUsersContent = "allusers";

	/**
	public boolean valid(String usr, String pwd) {
		for(User ur : this) {
			if ( usr.equals(ur.getName())
					&& pwd.equals(ur.getPassword()) ) {
				return true;
			}
		}
		return false;
	}
	**/
	
	static public boolean valid(String usr, String pwd, Connection conn) throws SQLException {
		boolean ret = false;
		
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
        
        return ret;
	}

	/**
	public User getUserByName(String name) {
		for(User ur : this) {
			if ( name.equals(ur.getName())) {
				return ur;
			}
		}
		return null;
	}
	 * @throws SQLException 
	**/
	
	static public User getUserByName(String name, Connection conn) throws SQLException {
		User usr = null;
		
		String sql = "select id, user_name from users where user_name = ?";
        PreparedStatement pstmt = conn.prepareStatement( sql );
        pstmt.setString( 1, name );
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
        	usr = new User(rs.getInt("id"), rs.getString("user_name"));
        }
        
        conn.close();
        
		return usr;
	}

	static public User getUserByID(int id, Connection conn) throws SQLException {
		User usr = null;
		
		String sql = "select id, user_name from users where id = ?";
        PreparedStatement pstmt = conn.prepareStatement( sql );
        pstmt.setInt( 1, id );
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
        	usr = new User(rs.getInt("id"), rs.getString("user_name"));
        }
        
        conn.close();
        
		return usr;
	}
}
