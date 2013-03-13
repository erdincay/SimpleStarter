package cs320.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs320.model.Users;

public class HtmlAPI {
	private static final String DefaultModernizr = "scripts/modernizr.custom.87909.js";
	
	private static final String fstrID = "id";
	private static final String fstrUsr = "username";
	private static final String fstrPwd = "password";
	private static final String fstrSubmit = "submit";
	private static final String fstrNext = "Next";
	private static final String fstrConfirm = "Confirm";
	private static final String fstrCancel = "Cancel";
	private static final String fstrTest = "So much typing test jobs are boring, do u need a favor? Here is a story: I went to see the doctor about my hearing loss and he gave me some medicine and told me to take two drops a day in my beer.I've been doing it for 5 days now and I still haven't noticed any improvement.";
	
	public static final String DefaultCSS = "styles/basic.css";
	public static final String DefaultScript = "scripts/global.js";
	
	public static void generateXhtmlHeader( HttpServletResponse response, String title, List<String> linksName) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType( "text/html" );
        out.println("<?xml version='1.0' encoding='ISO-8859-1' ?>" +
        		"<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
        out.println( "<html xmlns='http://www.w3.org/1999/xhtml' lang='en' >" +
        		"<head>" +
        		"<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1' />" +
        		"<title>" + title +	"</title>");
        if(linksName != null) {
        	for(String linkName : linksName) {
        		out.println("<link type='text/css' rel='stylesheet' href='" + linkName + "' media='screen'/>");
        	}
        }
        generateModernizrScript(response);
        //generateJQueryScript(response);
        out.println("</head>");
	}
	
	public static void generateHtml5Header( HttpServletResponse response, String title, List<String> linksName) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType( "text/html" );
        out.println("<!DOCTYPE html>");
        out.println( "<html lang='en' xml:lang='en'>" +
        		"<head>" +
        		"<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1' />" +
        		"<title>" + title +	"</title>");
        if(linksName != null) {
        	for(String linkName : linksName) {
        		out.println("<link type='text/css' rel='stylesheet' href='" + linkName + "' media='screen'/>");
        	}
        }
        generateModernizrScript(response);
        generateJQueryScript(response);
        out.println("</head>");
	}
	
	public static String generateLogLink(HttpServletRequest request, HttpServletResponse response){
		// check if a user has logged in or not
        if( request.getSession().getAttribute( fstrUsr ) == null ) {
        	return "<div id='log'><a href='Login' title='To Log In'>Login</a></div>";
        }
        else {
        	return "<div id='log'><a href='Logout' title='To Log Out'>Logout</a></div>";
        }        
	}
	
	public static void generateScript(HttpServletResponse response,String scriptPath) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<script type='text/javascript' src='" + scriptPath +"' ></script>");
	}

	private static void generateJQueryScript(HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js'></script>");
		out.println("<script type='text/javascript'>" +
				"if(!window.jQuery) {document.write( unescape ('%3Cscript type='text/javascript' src='scripts/jquery-1.9.0.js'%3E%3C/script%3E')) }" +
				"</script>");	
	}
	
	private static void generateModernizrScript(HttpServletResponse response) throws IOException {
		generateScript(response,DefaultModernizr);
	}
	
	public static String transPercent(Double cur, Double total, Integer accuracy) {
		NumberFormat nf  =  NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(accuracy);
		return nf.format(cur / total);
	}
	 
	public static Users getUsers(HttpServlet servlet) {
		return (Users) servlet.getServletContext().getAttribute(Users.fstrUsersContent);
	}
	
	//public static void setUsers(HttpServlet servlet, Users val) {
	//	servlet.getServletContext().setAttribute(Users.fstrUsersContent,val);
	//}
	
	//public static Projects getProjects(HttpServlet servlet) {
	//	return (Projects) servlet.getServletContext().getAttribute(Projects.fstrProjectsContent);
	//}
	
	//public static void setProjects(HttpServlet servlet, Projects val) {
	//	servlet.getServletContext().setAttribute(Projects.fstrProjectsContent, val);
	//}
	
	public static String getUsrFromSession(HttpServletRequest request) {
		return (String)request.getSession().getAttribute( fstrUsr );
	}
	
	public static void setUsrToSession(HttpServletRequest request,String val) {
		request.getSession().setAttribute( HtmlAPI.fstrUsr, val );
	}
	
	public static String getUsrAttrName() {
		return fstrUsr;
	}
	
	public static String getPwdAttrName() {
		return fstrPwd;
	}
	
	public static String getSubmitAttrName() {
		return fstrSubmit;
	}
	
	public static String getConfirmAttrName() {
		return fstrConfirm;
	}
	
	public static String getCancelAttrName() {
		return fstrCancel;
	}
	
	public static String getNextAttrName() {
		return fstrNext;
	}
	
	public static String getIdAttrName() {
		return fstrID;
	}
	
	public static String getTestStr() {
		return fstrTest;
	}
}
