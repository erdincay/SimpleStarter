package cs320.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.Calendar;
import java.util.Map;

import cs320.pattern.FactoryF;

public class ProjectD implements Comparable<ProjectD>{
	private long identify;
	private String title;
	private String description;
	
	private int fudTarget;
	private int fudDuration;
	private Date fudStartDate;
	
	private UserD author;
	private PledgesI plgs;
	private RewardsC rds;
	
	public ProjectD(long id, UserD author, String title, String description,
			int fudTarget, int fudDuration, Date fudStartDate) {
		this.identify = id;
		this.author = author;
		this.title = title;
		this.description = description;
		this.fudTarget = fudTarget;
		this.fudDuration = fudDuration;
		this.fudStartDate = fudStartDate;
		this.rds = FactoryF.getFactory().CreateRewards(getIdentity());
		this.plgs = FactoryF.getFactory().CreatePledges(getIdentity());
	}
	
	public UserD getAuthor() {
		return author;
	}

	public void setAuthor(UserD author) {
		this.author = author;
	}



	@Override
	public int compareTo(ProjectD p) {
		
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

	public void addPledged(UserD usr,PledgeD plg) {
		plgs.savePledge(usr, plg);
	}
	
	public Map<String,PledgeD> getPledges() {
		return plgs.generatePledges();
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
	
	public RewardsC getRewards(){
		return rds.generateRewards();
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
	
	public boolean checkPledgeByUser(UserD usr) throws SQLException {
		return plgs.checkPledgeByUser(usr.getName());
	}
	
}
