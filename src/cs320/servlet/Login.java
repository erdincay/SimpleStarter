package cs320.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;

import cs320.api.HtmlAPI;
import cs320.model.Factory;
import cs320.model.Users;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	@Override
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
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 // check if a user has logged in or not
        if( request.getSession().getAttribute( HtmlAPI.getUsrAttrName() ) != null )
        {
            response.sendRedirect( "DisplayAllProjects" );
            return;
        }
        
		request.getRequestDispatcher( "/WEB-INF/Login.jsp" ).forward(
				request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strUsr = request.getParameter(HtmlAPI.getUsrAttrName());
		String strPwd = request.getParameter(HtmlAPI.getPwdAttrName());
		String strSubmit = request.getParameter(HtmlAPI.getSubmitAttrName());
		
		if(strSubmit != null && strSubmit.equals(HtmlAPI.getCancelAttrName())) {
        	response.sendRedirect("DisplayAllProjects");
        	return;
        }
		
		try {
	        if(Users.valid(strUsr, strPwd, Factory.getDbConnection())) {
	        	HtmlAPI.setUsrToSession(request, strUsr);
	            response.sendRedirect( "DisplayAllProjects" );
	        }
	        else {
	        	response.sendRedirect( "Login" );
	        }
		} 
		catch (SQLException e) {
			throw new ServletException( e );
		}
	}

}
