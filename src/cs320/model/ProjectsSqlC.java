package cs320.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import cs320.pattern.FactorySqlF;

public class ProjectsSqlC extends ArrayList<ProjectD> implements ProjectsI{

	private static final long serialVersionUID = -5769981599803801854L;

	public ProjectD getProjectByID(long identify) {

		ProjectD prj = null;
		try {
			Connection conn = FactorySqlF.getDbConnection();

			String sql = "select * from projects where id = ?";
			PreparedStatement pstmt = conn.prepareStatement( sql );
			pstmt.setLong(1, identify);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String description = rs.getString("description");
				int target = rs.getInt("target");
				int duration = rs.getInt("duration");
				Date start_date = rs.getDate("start_date");
				UserD author = ((UsersSqlC) FactorySqlF.getUsers()).getUserByID(rs.getInt("author_id"));

				prj = new ProjectD( id, author, title, description, target, duration, start_date );
			}

			conn.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}

		return prj;
	}

	public ProjectsI generateProjects(int records_num) {

		ProjectsI prjs = new ProjectsSqlC();
		try {
			Connection conn = FactorySqlF.getDbConnection();
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( "select * from projects where datediff( date_add( start_date, INTERVAL duration day) , curdate()) >0 order by datediff( date_add( start_date, INTERVAL duration day) , curdate())" );

			while( rs.next() )
			{
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String description = rs.getString("description");
				int target = rs.getInt("target");
				int duration = rs.getInt("duration");
				Date start_date = rs.getDate("start_date");
				UserD author = ((UsersSqlC) FactorySqlF.getUsers()).getUserByID(rs.getInt("author_id"));

				prjs.add( new ProjectD( id, author, title, description, target, duration, start_date ) );
			}

			conn.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return prjs;
	}

	public long saveProject(UserD author, String title, String description,
			int target, int duration, Date start_date) {

		long id = 0;
		
		try {
			Connection conn = FactorySqlF.getDbConnection();
			
			String sql = "insert into projects (`title`, `description`, `target`, `duration`, `start_date`, `author_id`) values (?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			pstmt.setString( 1, title );
			pstmt.setString( 2, description );
			pstmt.setInt( 3, target );
			pstmt.setInt( 4, duration );
			pstmt.setDate( 5, new java.sql.Date(start_date.getTime()) );
			pstmt.setInt(6, author.getId());

			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			
			id = -1;
			if (rs.next()) {  
				id = rs.getLong(1);
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}
}
