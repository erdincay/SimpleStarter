package cs320.model;

public class Reward implements Comparable<Reward>{
	private int amount;
	private String description;
	
	public Reward(int amount, String description) {
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
	public int compareTo(Reward r) {
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
