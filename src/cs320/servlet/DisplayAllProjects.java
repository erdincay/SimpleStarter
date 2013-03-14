package cs320.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs320.model.ProjectsI;
import cs320.pattern.FactoryF;

/**
 * Servlet implementation class DisplayAllProjects
 */
@WebServlet("/DisplayAllProjects")
public class DisplayAllProjects extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int maxRecordsPerPage = 100;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DisplayAllProjects() {
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
		// get the data
		ProjectsI prjs = FactoryF.getProjects().generateProjects(maxRecordsPerPage);
		
		request.setAttribute( "prjs", prjs );
		request.getRequestDispatcher( "/WEB-INF/DisplayAllProjects.jsp" ).forward(
				request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
