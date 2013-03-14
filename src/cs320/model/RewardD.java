package cs320.model;

public class RewardD implements Comparable<RewardD>{
	private int amount;
	private String description;
	
	public RewardD(int amount, String description) {
		this.amount = amount;
		this.description = description;
	}

	public int getpAmount() {
		return amount;
	}

	public void setpAmount(int amount) {
		this.amount = amount;
	}

	public String getrDescription() {
		return description;
	}

	public void setrDescription_(String description) {
		this.description = description;
	}

	@Override
	public int compareTo(RewardD r) {
		if(this.getpAmount() < r.getpAmount()) {
			return -1;
		}
		else if(getpAmount() > r.getpAmount()) {
			return 1;
		}
		else {
			return 0;
		}
	}

	
}
