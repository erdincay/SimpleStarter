package cs320.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.XMLWriter;

import cs320.pattern.FactoryXmlF;
import cs320.pattern.FactoryXmlF.DB;

public class UsersXmlC extends HashSet<UserD> implements UsersC {
	
	private static final long serialVersionUID = 1542603992912134345L;
	
	private static final String fstrUsers = "users";
	private static final String fstrUser = "user";
	private static final String fstrName = "name";
	private static final String fstrPassword = "password";
	
	private int lastAllocId;
	
	public UsersXmlC() {
		lastAllocId = 0;
		loadUsers();
	}
	
	private int generateID() {
		return lastAllocId++;
	}
	
	private int loadUsers() {
		return parseXmlToUsers(FactoryXmlF.getDbConnection(DB.USERS));
	}

	@SuppressWarnings("unchecked")
	private int parseXmlToUsers(String path) {
		int usr_num = 0;
		
		Document dom = FactoryXmlF.load(path);
		if(dom != null) {
			String xPath = "//" + fstrUsers + "/" + fstrUser;
			List<? extends Node> eUsrList = dom.selectNodes( xPath );
			usr_num = eUsrList.size();
			if (usr_num > 0) {
				for (Iterator<? extends Node> it = (Iterator<? extends Node>) eUsrList.iterator(); it.hasNext();) {
					Element eUsr = (Element) it.next();
					String strName = eUsr.elementTextTrim(fstrName);
					String strPassword = eUsr.elementTextTrim(fstrPassword);
					
					UserD usr = new UserD(generateID(),strName, strPassword);
					this.add(usr);
				}
			}
		}
		
		return usr_num;
	}
	
	private Document saveUsersToxml(UsersC usrs) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement( fstrUsers );
		for(UserD usr : this) {
			Element eUsr = root.addElement(fstrUser);
			eUsr.addElement(fstrName).addText(usr.getName());
			eUsr.addElement(fstrPassword).addText(usr.getPassword());
		}
		return document;
	}
	
	public void saveXml() {
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileWriter( FactoryXmlF.getDbConnection(DB.USERS) ));
			writer.write( saveUsersToxml(this) );
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public boolean valid(String usr, String pwd) {
		for(UserD ur : this) {
			if ( usr.equals(ur.getName())
					&& pwd.equals(ur.getPassword()) ) {
				return true;
			}
		}
		return false;

	}
	
	public UserD getUserByName(String name){
		for(UserD ur : this) {
			if ( name.equals(ur.getName())) {
				return ur;
			}
		}
		return null;
	}

	public UserD getUserByID(int id){
		for(UserD ur : this) {
			if ( ur.getId() == id) {
				return ur;
			}
		}
		return null;
	}

	@Override
	public boolean saveUser(String usrName, String passWord, String firstName,
			String lastName) {
		boolean ret =this.add(new UserD(generateID(), usrName, passWord, firstName, lastName));
		saveXml();
		
		return ret;
	}
}
