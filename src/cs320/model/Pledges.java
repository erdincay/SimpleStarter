package cs320.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Pledges extends HashMap<String, Pledge> {
	private static final long serialVersionUID = 1L;

	private long projectId;
	
	public Pledges(long id) throws SQLException {
		super();
		this.projectId = id;
		generatePledges(Factory.getDbConnection());
	}

	private long getProjectId() {
		return projectId;
	}
	
	public int totalPledges() {
		int total = 0;
		for(Pledge plg : this.values()) {
			total += plg.getPledged();
		}
		return total;
	}
	
	public boolean checkPledgeByUser(String key) {
		return this.containsKey(key);
	}
	
	private void generatePledges(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery( "select * from pledges where project_id = " + getProjectId() + " group by user_id" );

		while( rs.next() )
		{
			int pledeged_amount = rs.getInt("pledeged_amount");
			int reward_amount = rs.getInt("reward_amount");
			int user_id = rs.getInt("user_id");
			User usr = Users.getUserByID(user_id, Factory.getDbConnection());
			if(usr != null) {
				this.put(usr.getName(), new Pledge(pledeged_amount,reward_amount));
			}
		}

		conn.close();
	}
	
	public void savePledge(User usr, Pledge pl) throws SQLException {

		Connection conn = Factory.getDbConnection();
		String sql = "insert into pledges (`pledeged_amount`, `reward_amount`, `user_id`, `project_id`) values (?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement( sql );
		pstmt.setInt( 1, pl.getPledged() );
		pstmt.setInt( 2, pl.getRewardAmount() );
		pstmt.setInt( 3, usr.getId() );
		pstmt.setLong( 4, this.getProjectId() );

		pstmt.executeUpdate();

		conn.close();
		
		this.put(usr.getName(), pl);
	}
}
