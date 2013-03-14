package cs320.model;

import java.util.Map;

public interface PledgesI {
	public int totalPledges();
	public boolean checkPledgeByUser(String username);
	public Map<String,PledgeD> generatePledges();
	public void savePledge(UserD usr, PledgeD pl);
}
