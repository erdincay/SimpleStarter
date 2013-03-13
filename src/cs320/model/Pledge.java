package cs320.model;

public class Pledge {
	private int pledged;
	private int rewardAmount;
	
	public Pledge(int pledged, int rewardAmount) {
		this.pledged = pledged;
		this.rewardAmount = rewardAmount;
	}

	public int getPledged() {
		return pledged;
	}

	public void setPledged(int pledged) {
		this.pledged = pledged;
	}

	public int getRewardAmount() {
		return rewardAmount;
	}

	public void setRewardAmount(int rewardAmount) {
		this.rewardAmount = rewardAmount;
	}
	
}
