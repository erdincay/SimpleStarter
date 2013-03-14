package cs320.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs320.pattern.FactorySqlF;

public class RewardsSqlC extends RewardsC {

	private static final long serialVersionUID = 7450890812836176844L;
	private long projectId;
	
	public RewardsSqlC(long id) throws SQLException {
		projectId = id;
		loadRewards();
	}
	
	private long getProjectId() {
		return projectId;
	}

	private void loadRewards() {

		try {
			Connection conn = FactorySqlF.getDbConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery( "select * from rewards where project_id = " + getProjectId() + " order by amount" );

			while( rs.next() )
			{
				int amount = rs.getInt("amount");
				String description = rs.getString("description");
				
				this.add( new RewardD(amount,description) );
			}

			conn.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void saveReward(RewardD rd) {

		try {
			Connection conn = FactorySqlF.getDbConnection();
			String sql = "insert into rewards (`amount`, `description`, `project_id`) values (?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement( sql );
			pstmt.setInt( 1, rd.getpAmount() );
			pstmt.setString( 2, rd.getrDescription() );
			pstmt.setLong(3, this.getProjectId());

			pstmt.executeUpdate();

			conn.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.add(rd);
	}
}
