package cs320.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs320.api.HtmlAPI;
import cs320.model.Factory;
import cs320.model.Projects;
import cs320.model.User;
import cs320.model.Users;

/**
 * Servlet implementation class CreateProject
 */
@WebServlet("/CreateProject")
public class CreateProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String fstrDescription = "description";
	private static final String fstrTitle = "title";
	private static final String fstrTarget = "target";
	private static final String fstrDate = "date";
	private static final String fstrDuration = "duration";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateProject() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private User checkValid(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
    	// check if a user has logged in or not
    	String strUsr = HtmlAPI.getUsrFromSession(request);
    	if( strUsr == null )
    	{
    		response.sendRedirect( "Login" );
    		return null;
    	}
    	
    	User usr = Users.getUserByName(strUsr,Factory.getDbConnection());
    	if(usr == null) {
    		response.sendRedirect( "Login" );
    		return null;
    	}
    	
    	return usr;
    }
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User usr = null;
		try {
			usr = checkValid(request,response);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		
		if(usr == null) {
			return;
		}
		
		request.getRequestDispatcher( "/WEB-INF/CreateProject.jsp" ).forward(
				request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User usr = null;
		try {
			usr = checkValid(request,response);
		} 
		catch (SQLException e) {
			throw new ServletException(e);
		}
		
		if(usr == null) {
			return;
		}
		        
		// get the user input
		String submit = request.getParameter( HtmlAPI.getSubmitAttrName() );
        //String name = request.getParameter( fstrName );
        String title = request.getParameter( fstrTitle );
        String description = request.getParameter( fstrDescription );
        String strTarget = request.getParameter( fstrTarget );
        String strDate = request.getParameter( fstrDate );
        String strDuration = request.getParameter( fstrDuration );
        
        if(submit != null && submit.equals(HtmlAPI.getCancelAttrName())) {
        	response.sendRedirect("DisplayAllProjects");
        	return;
        }
        
        if ((strDate == null || strDate.trim().length() == 0 
        		|| strDuration == null || strDuration.trim().length() == 0
        		|| strTarget == null || strTarget.trim().length() == 0)) {
        	
        	response.sendRedirect("CreateProject");
        	return;
        }
        //translate input
        int target;
        int duration;
        try {
        	target = Integer.parseInt(strTarget);
        	duration = Integer.parseInt(strDuration);
        } catch (NumberFormatException e) {
        	e.printStackTrace();
        	response.sendRedirect("CreateProject");
        	return;
        }
        
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        try {
        	date = df.parse(strDate);
        } catch (ParseException e) {
        	e.printStackTrace();
        	response.sendRedirect("CreateProject");
        	return;
        }
        // create the new project and add to projects
		try {
			long id = Projects.saveProject(usr, title, description, target, duration, date, Factory.getDbConnection());
	        
			// send the user to add rewards
	        response.sendRedirect("AddRewards?" + HtmlAPI.getIdAttrName() + "="
	        		+ id);
		} 
		catch (SQLException e) {
			throw new ServletException(e);
		}
	}

}
