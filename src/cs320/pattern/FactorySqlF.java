package cs320.pattern;

import java.sql.SQLException;

import org.dom4j.Element;

import cs320.model.PledgesI;
import cs320.model.PledgesSqlC;
import cs320.model.ProjectsI;
import cs320.model.ProjectsSqlC;
import cs320.model.RewardsC;
import cs320.model.RewardsSqlC;
import cs320.model.UsersC;
import cs320.model.UsersSqlC;

public class FactorySqlF extends FactoryF {

	static private final String fstrUrl = "url";
	static private final String fstrUsr = "usr";
	static private final String fstrPwd = "pwd";
	
	static private String strUrl;
	static private String strUsr;
	static private String strPwd;
	
	public FactorySqlF(Element el) {
		super();
		parseCfg(el);
	}
	
	private void parseCfg(Element el) {
		strUrl = el.elementTextTrim(fstrUrl);
		strUsr = el.elementTextTrim(fstrUsr);
		strPwd = el.elementTextTrim(fstrPwd);
	}

	public ProjectsI CreateProjects() {
		return new ProjectsSqlC();
	}

	public UsersC CreateUsers() {
		return new UsersSqlC();
	}

	public RewardsC CreateRewards(long id) {
		RewardsC rds = null;
		
		try {
			rds = new RewardsSqlC(id);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rds;
	}

	public PledgesI CreatePledges(long id) {
		return new PledgesSqlC(id);
	}
	
	public static java.sql.Connection getDbConnection() throws SQLException {
        java.sql.Connection conn = java.sql.DriverManager.getConnection( strUrl, strUsr, strPwd );
        
        return conn;
	}
}
