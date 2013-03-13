package cs320.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.LinkedList;

public class Rewards extends LinkedList<Reward> {
	private static final long serialVersionUID = 1L;

	private long projectId;
	
	public Rewards(long id) throws SQLException {
		super();
		projectId = id;
		generateRewards(Factory.getDbConnection());
	}
	
	public Reward getRewardByAmount(int amount) {
		int i = 0;
		for(Reward rd : this) {
			if(amount == rd.getpAmount()) {
				return rd;
			}
			else if(amount < rd.getpAmount()) {
				if(i > 0) {
					return this.get(i - 1);
				}
				else {
					break;
				}
			}
			i++;
		}
		return null;
	}

	public void sortByAmount() {
		Collections.sort(this);
	}
	
	private long getProjectId() {
		return projectId;
	}

	private void generateRewards(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery( "select * from rewards where project_id = " + getProjectId() + " order by amount" );

		while( rs.next() )
		{
			int amount = rs.getInt("amount");
			String description = rs.getString("description");
			
			this.add( new Reward(amount,description) );
		}

		conn.close();
	}
	
	public void saveReward(Reward rd, Connection conn) throws SQLException {

		String sql = "insert into rewards (`amount`, `description`, `project_id`) values (?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement( sql );
		pstmt.setInt( 1, rd.getpAmount() );
		pstmt.setString( 2, rd.getrDescription() );
		pstmt.setLong(3, this.getProjectId());

		pstmt.executeUpdate();

		conn.close();
		
		this.add(rd);
	}
}
