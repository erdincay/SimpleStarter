package cs320.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * the purpose of this class is to 
 * avoid input a few projects to debug every time the application reboot 
 * since we do not use database now. Thus I think the flat database -- xml 
 * may be a good idea.
 */
public class Factory {
	private static final String fstrProjects = "projects";
	private static final String fstrProject = "project";
	private static final String fstrID = "id";
	private static final String fstrTitle = "title";
	private static final String fstrAuthor = "author";
	private static final String fstrTarget = "target";
	private static final String fstrDate = "date";
	private static final String fstrDuration = "duration";
	private static final String fstrDescription = "decription";
	private static final String fstrRewards = "rewards";
	private static final String fstrReward = "reward";
	private static final String fstrAmount = "amount";
	
	private static final String fstrUsers = "users";
	private static final String fstrUser = "user";
	private static final String fstrName = "name";
	private static final String fstrPassword = "password";
	
	/**
	 * private for static
	 */
	private Factory() {
		super();
	}

	private static Document load(String filename) {
		Document document = null;  
		try {  
			SAXReader saxReader = new SAXReader();  
			document = saxReader.read(new File(filename));  
		} catch (Exception ex) {  
			ex.printStackTrace();  
		}  
		return document;  
	}
	
	private static Document saveUsersToxml(Users usrs) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement( fstrUsers );
		for(User usr : usrs) {
			Element eUsr = root.addElement(fstrUser);
			eUsr.addElement(fstrName).addText(usr.getName());
			eUsr.addElement(fstrPassword).addText(usr.getPassword());
		}
		return document;
	}
	
	private static Users parseXmlToUsers(String path) {
		Document dom = load(path);
		if(dom != null) {
			Element eUsrs = dom.getRootElement();
			List<Element> eUsrList = eUsrs.elements(fstrUser);
			if (eUsrList.size() > 0) {
				Users usrs = new Users();
				for (Iterator<Element> it = eUsrList.iterator(); it.hasNext();) {
					Element eUsr = (Element) it.next();
					String strID = eUsr.elementTextTrim(fstrID);
					String strName = eUsr.elementTextTrim(fstrName);
					String strPassword = eUsr.elementTextTrim(fstrPassword);
					
					int id = Integer.parseInt(strID);
					
					User usr = new User(id, strName, strPassword);
					usrs.add(usr);
				}
				return usrs;
			}
		}
		return null;
	}

	private static Projects parseXmlToProjects(String path) throws SQLException	{
		Document dom = load(path);
		if (dom != null) {
			Element ePrjs = dom.getRootElement();
			List<Element> ePrjList = ePrjs.elements(fstrProject);
			if (ePrjList.size() > 0) {
				Projects prjs = new Projects();
				for (Iterator<Element> it = ePrjList.iterator(); it.hasNext();) {
					Element ePrj = (Element) it.next();
					String strId = ePrj.elementTextTrim(fstrID);
					String strTitle = ePrj.elementTextTrim(fstrTitle);
					String strAuthor = ePrj.elementTextTrim(fstrAuthor);
					String strTarget = ePrj.elementTextTrim(fstrTarget);
					String strDate = ePrj.elementTextTrim(fstrDate);
					String strDuration = ePrj.elementTextTrim(fstrDuration);
					String strDescription = ePrj
							.elementTextTrim(fstrDescription);

					long id = Long.parseLong(strId);
					int iDuration = Integer.parseInt(strDuration);
					int iTarget = Integer.parseInt(strTarget);
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
					Date date;
					try {
						date = df.parse(strDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
					User usr = Users.getUserByName(strAuthor, getDbConnection());
					Project prj = new Project(id, usr, strTitle,
							strDescription, iTarget, iDuration, date);

					Element eRws = ePrj.element(fstrRewards);
					if (eRws != null) {
						List<Element> eRwList = eRws.elements(fstrReward);
						if (eRwList.size() > 0) {
							for (Iterator<Element> i = eRwList.iterator(); i
									.hasNext();) {
								Element eRw = (Element) i.next();
								String strAmount = eRw.elementText(fstrAmount);
								String strDsp = eRw
										.elementText(fstrDescription);

								int iAmount = Integer.parseInt(strAmount);
								Reward rw = new Reward(iAmount, strDsp);
								prj.getRewards().add(rw);
							}
						}
					}
					prjs.add(prj);
				}
				return prjs;
			}
		}
		
		return null;
	}
	
	private static String getWebRoot() {
		String path = Projects.class.getClassLoader().getResource("/").getPath();
		for (int i = 0; i < 3; i++) {
			path = path.substring(0, path.lastIndexOf("/"));
		}
		return path;
	}
	
	public static Projects createProjects() throws SQLException{
		String path = getWebRoot() + "/data/" + fstrProjects + ".xml";
		Projects prjs = parseXmlToProjects(path);
		if(prjs == null) {
			prjs = new Projects();
		}
		return prjs;
	}
	
	public static Users createUsers() {
		String path = getWebRoot() + "/data/" + fstrUsers + ".xml";
		Users usrs = parseXmlToUsers(path);
		if(usrs == null) {
			usrs = new Users();
		}
		return usrs;
	}
	
	public static void saveXml(Users usrs) {
		String path = getWebRoot() + "/data/" + fstrUsers + ".xml";
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileWriter( path ));
			writer.write( saveUsersToxml(usrs) );
			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static java.sql.Connection getDbConnection() throws SQLException {
        String url = "jdbc:mysql://cs3.calstatela.edu/cs320stu61";
        String username = "cs320stu61";
        String password = "8i8b8Q!9";

        java.sql.Connection conn = java.sql.DriverManager.getConnection( url, username, password );
        
        return conn;
	}
}
