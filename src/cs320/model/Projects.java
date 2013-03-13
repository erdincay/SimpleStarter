package cs320.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

public class Projects extends LinkedList<Project> {
	private static final long serialVersionUID = 1L;

	public static final String fstrProjectsContent = "allprojects";

	public Projects() throws SQLException {
		super();
		generateProjects(Factory.getDbConnection());
	}

	/**
	public Project getProjectByID(long id) {
		for(Project p : this) {
			if(p.getIdentity() == id) {
				return p;
			}
		}

		return null;
	}
	**/
	
	public void sortByLeftDays() {
		Collections.sort(this);
	}

	static public Project getProjectByID(long identify, Connection conn) throws SQLException {

		Project prj = null;

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
			User author = Users.getUserByID(rs.getInt("author_id"), Factory.getDbConnection());

			prj = new Project( id, author, title, description, target, duration, start_date );
		}

		conn.close();

		return prj;
	}

	private void generateProjects(Connection conn) throws SQLException {

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
			User author = Users.getUserByID(rs.getInt("author_id"), Factory.getDbConnection());

			this.add( new Project( id, author, title, description, target, duration, start_date ) );
		}

		conn.close();
	}

	static public long saveProject(User author, String title, String description,
			int target, int duration, Date start_date, Connection conn) throws SQLException {

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
		
		long id = -1;
		if (rs.next()) {  
			id = rs.getLong(1);
		}

		conn.close();

		return id;
	}
}
