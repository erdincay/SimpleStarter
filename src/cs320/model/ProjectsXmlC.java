package cs320.model;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.XMLWriter;

import cs320.pattern.FactoryXmlF;
import cs320.pattern.FactoryXmlF.DB;
import cs320.pattern.Observable;
import cs320.pattern.Observable.event;
import cs320.pattern.Observer;

public class ProjectsXmlC extends LinkedList<ProjectD> implements ProjectsI, Observer{

	private static final long serialVersionUID = -1725757326755819811L;
	
	private static final String fstrProjects = "projects";
	private static final String fstrProject = "project";
	private static final String fstrTitle = "title";
	private static final String fstrAuthor = "author";
	private static final String fstrTarget = "target";
	private static final String fstrDate = "date";
	private static final String fstrDuration = "duration";
	private static final String fstrDescription = "decription";
	private static final String fstrRewards = "rewards";
	private static final String fstrReward = "reward";
	private static final String fstrAmount = "amount";
	private static final String fstrPledges = "pledges";
	private static final String fstrPledge = "pledge";
	private static final String fstrDonor = "donor";
	
	private long lastAllocId;
	
	public ProjectsXmlC() {
		lastAllocId = 0;
		loadProjects();
		sortByLeftDays();
	}

	private long generateID() {
		return ++lastAllocId;
	}
	
	private void sortByLeftDays() {
		Collections.sort(this);
	}
	
	public ProjectD getProjectByID(long id) {
		for(ProjectD prj : this){
			if(prj.getIdentity() == id) {
				return prj;
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private int parseXmlToProjects(String path) {
		int prj_num = 0;
		
		Document dom = FactoryXmlF.load(path);
		if (dom != null) {
			String xProject = "//" + fstrProjects + "/" + fstrProject;
			List<? extends Node> ePrjList = dom.selectNodes( xProject );
			if (ePrjList.size() > 0) {
				for ( Node itPrj : ePrjList	) 
				{
					Element ePrj = (Element) itPrj;
					String strTitle = ePrj.elementTextTrim(fstrTitle);
					String strAuthor = ePrj.elementTextTrim(fstrAuthor);
					String strTarget = ePrj.elementTextTrim(fstrTarget);
					String strDate = ePrj.elementTextTrim(fstrDate);
					String strDuration = ePrj.elementTextTrim(fstrDuration);
					String strDescription = ePrj.elementTextTrim(fstrDescription);

					int iDuration = Integer.parseInt(strDuration);
					int iTarget = Integer.parseInt(strTarget);
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
					Date date;
					try {
						date = df.parse(strDate);
					} 
					catch (ParseException e) {
						e.printStackTrace();
						continue;
					}
					
					UserD usr = FactoryXmlF.getUsers().getUserByName(strAuthor);
					if(usr == null) {
						continue;
					}
					ProjectD prj = new ProjectD(generateID(), usr, strTitle,
							strDescription, iTarget, iDuration, date);
					prj_num++;
					
					String xReward = xProject + "[" + prj_num + "]/" + fstrRewards + "/" + fstrReward;
					List<? extends Node> eRdList = dom.selectNodes( xReward );
					RewardsC rds = prj.getRewards();
					((Observable)rds).addObserver(this);
					if (eRdList.size() > 0) {
						for (Node itRd : eRdList) {
							Element eRw = (Element) itRd;
							String strAmount = eRw.elementText(fstrAmount);
							String strDsp = eRw.elementText(fstrDescription);

							int iAmount = Integer.parseInt(strAmount);
							RewardD rw = new RewardD(iAmount, strDsp);
							rds.add(rw);
						}
					}
					
					String xPledge = xProject + "[" + prj_num + "]/" + fstrPledges + "/" + fstrPledge;
					List<? extends Node> ePlList = dom.selectNodes( xPledge );
					PledgesXmlC pls = (PledgesXmlC) prj.getPledges();
					pls.addObserver(this);
					if (ePlList.size() > 0) {
						for (Node itRd : ePlList) {
							Element ePl = (Element) itRd;
							String strAmount = ePl.elementText(fstrAmount);
							String strReward = ePl.elementText(fstrReward);
							String strDonor = ePl.elementText(fstrDonor);

							int iAmount = Integer.parseInt(strAmount);
							int iReward = Integer.parseInt(strReward);
							
							PledgeD plg = new PledgeD(iAmount, iReward);
							pls.put(strDonor, plg);
						}
					}
					
					this.add(prj);

				}
			}
		}
		return prj_num;
	}

	public int loadProjects() {

		return parseXmlToProjects(FactoryXmlF.getDbConnection(DB.PROJECTS));
	}

	private Document saveProjectsToxml(ProjectsI prjs) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement( fstrProjects );
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		for(ProjectD prj : this) {
			Element ePrj = root.addElement(fstrProject);
			ePrj.addElement(fstrTitle).addText(prj.getTitle());
			ePrj.addElement(fstrAuthor).addText(prj.getAuthor().getName());
			ePrj.addElement(fstrTarget).addText(((Integer)prj.getFudTarget()).toString());
			
			ePrj.addElement(fstrDate).addText(df.format(prj.getFudStartDate()));
			ePrj.addElement(fstrDuration).addText(((Integer)prj.getFudDuration()).toString());
			ePrj.addElement(fstrDescription).addText(prj.getDescription());
			
			if (prj.getRewards().size() > 0) {
				Element eRwds = ePrj.addElement(fstrRewards);
				for (RewardD rwd : prj.getRewards()) {
					Element eRwd = eRwds.addElement(fstrReward);
					eRwd.addElement(fstrAmount).addText(
							((Integer) rwd.getpAmount()).toString());
					eRwd.addElement(fstrDescription).addText(
							rwd.getrDescription());
				}
			}
			
			if (prj.getPledges().size() > 0) {
				Element ePlgs = ePrj.addElement(fstrPledges);
				for (Entry<String, PledgeD> itPlg : prj.getPledges().entrySet()) {
					Element ePlg = ePlgs.addElement(fstrPledge);
					String strDonor = itPlg.getKey();
					PledgeD plg = itPlg.getValue();
					ePlg.addElement(fstrAmount).addText(
							((Integer) plg.getPledged()).toString());
					ePlg.addElement(fstrReward).addText(
							((Integer) plg.getRewardAmount()).toString());
					ePlg.addElement(fstrDonor).addText(strDonor);
				}
			}
		}
		
		return document;
	}
	
	public void saveXml() {
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileWriter( FactoryXmlF.getDbConnection(DB.PROJECTS) ));
			writer.write( saveProjectsToxml(this) );
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public long saveProject(UserD author, String title, String description,
			int target, int duration, Date start_date) {

		ProjectD prj = new ProjectD(generateID(), author, title,
				description, target, duration, start_date);
		this.add(prj);
		((Observable)prj.getRewards()).addObserver(this);
		((Observable)prj.getPledges()).addObserver(this);
		
		saveXml();
		
		return prj.getIdentity();
	}

	@Override
	public ProjectsI generateProjects(int records_num) {
		sortByLeftDays();
		return this;
	}

	public void update(Observable that, Object o) {
		if(((that instanceof RewardsXmlC) || (that instanceof PledgesXmlC)) 
				&& o == event.SAVE){
			saveXml();
		}
	}
}
