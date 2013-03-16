package cs320.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs320.api.HtmlAPI;
import cs320.pattern.FactoryF;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private static final int min_length = 4;
	
    /**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		try
        {
            Class.forName( "com.mysql.jdbc.Driver" );
        }
        catch( ClassNotFoundException e )
        {
            throw new ServletException( e );
        }
	}
	
	private List<String> valid(String strUsr,	String strPwd, String strRtype,	String strFirstName, String strLastName) {
		List<String> em = new ArrayList<String>(); 
		
		if(strUsr.isEmpty() || strUsr.length() < min_length) {
			em.add("username should >= " + min_length);
		}
		if(strPwd.isEmpty() || strPwd.length() < min_length) {
			em.add("password should >= " + min_length);
		}
		if(FactoryF.getUsers().getUserByName(strUsr)!= null) {
			//user name is in used
			em.add("the user name is in used.");
		}
		if(!strPwd.equals(strRtype)) {
			em.add("passwords shoud be the same from each input");
		}
		if(strFirstName.isEmpty() || strLastName.isEmpty() ) {
			em.add("firstName or lastName can not be empty");
		}
				
		return em;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.getRequestDispatcher( "/WEB-INF/Register.jsp" ).forward(
				request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strUsr = request.getParameter(HtmlAPI.getUsrAttrName());
		String strPwd = request.getParameter(HtmlAPI.getPwdAttrName());
		String strRtype = request.getParameter("retypePassword");
		String strFirstName = request.getParameter("firstName");
		String strLastName = request.getParameter("lastName");
		String strSubmit = request.getParameter(HtmlAPI.getSubmitAttrName());
		
		if(strSubmit != null && strSubmit.equals(HtmlAPI.getCancelAttrName())) {
        	response.sendRedirect("DisplayAllProjects");
        	return;
        }
		
		List<String> errorMessage= valid(strUsr,strPwd,strRtype,strFirstName,strLastName);
		if(!errorMessage.isEmpty()) {
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher( "/WEB-INF/Register.jsp" ).forward(
					request, response );
			return;
		}
		
		if(FactoryF.getUsers().saveUser(strUsr, strPwd, strFirstName, strLastName)) {
			//successfully saved
			request.getSession().invalidate();
			HtmlAPI.setUsrToSession(request, strUsr);
			response.sendRedirect( "DisplayAllProjects" );
		}
		else {
			//wrong for saving the usr to database
			errorMessage.add("occurs problem while saving, change the input and try again");
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher( "/WEB-INF/Register.jsp" ).forward(
					request, response );
		}
	}

}
