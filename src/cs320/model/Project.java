package cs320.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.Calendar;

public class Project implements Comparable<Project>{
	private long identify;
	private String title;
	private String description;
	
	private int fudTarget;
	private int fudDuration;
	private Date fudStartDate;
	
	private Rewards rds;
	private User author;
	private Pledges plgs;
	
	public Project(long id, User author, String title, String description,
			int fudTarget, int fudDuration, Date fudStartDate) throws SQLException {
		this.identify = id;
		this.author = author;
		this.title = title;
		this.description = description;
		this.fudTarget = fudTarget;
		this.fudDuration = fudDuration;
		this.fudStartDate = fudStartDate;
		this.rds = new Rewards(getIdentity());
		this.plgs = new Pledges(getIdentity());
	}
	
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}



	@Override
	public int compareTo(Project p) {
		
		if(this.getFudLeftCals() < p.getFudLeftCals()) {
			return -1;
		}
		else if(this.getFudLeftCals() > p.getFudLeftCals()) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public int getPledged() {
		return plgs.totalPledges();
	}

	public void addPledged(User usr,Pledge plg) throws SQLException {
		plgs.savePledge(usr, plg);
	}
	
	public String getAuthorName() {
		return this.author.getName();
	}
	public void setAuthorName(String authorName) {
		this.author.setName(authorName);
	}
	public long getIdentity() {
		return identify;
	}
	public void setIdentity(long pIdentity) {
		this.identify = pIdentity;
	}
	public String getTitle() {
		return title;
	}
	public void setTtile(String pTtile) {
		this.title = pTtile;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String pDescription) {
		this.description = pDescription;
	}
	public int getFudTarget() {
		return fudTarget;
	}
	public void setFudTarget(int fudTarget) {
		this.fudTarget = fudTarget;
	}
	public int getFudDuration() {
		return fudDuration;
	}
	public void setFudDuration(int fudDuration) {
		this.fudDuration = fudDuration;
	}
	public Date getFudStartDate() {
		return fudStartDate;
	}
	public void setFudStartDate(Date fudStartDate) {
		this.fudStartDate = fudStartDate;
	}
	public Rewards getRewards() {
		return rds;
	}
	public void setRewards(Rewards rds) {
		this.rds = rds;
	}
	
	public long getFudLeftCals() {
		Calendar curCal = Calendar.getInstance(); 
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(fudStartDate);
		endCal.add(Calendar.DAY_OF_YEAR, fudDuration);
		
		long daysleft = 0;
		if(curCal.before(endCal)) {
			for (; (curCal.before(endCal) /*&& (curDay < endDay)*/); daysleft++) {
				curCal.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		else {
			for(; (endCal.before(curCal) /*&& (endDay < curDay)*/);daysleft++) {
				endCal.add(Calendar.DAY_OF_MONTH, 1);
			}
			daysleft = -daysleft;
		}
		
		return daysleft;
	}
	
	public boolean checkPledgeByUser(User usr) {
		return plgs.checkPledgeByUser(usr.getName());
	}
	
}
