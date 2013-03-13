package cs320.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs320.model.Projects;

/**
 * Servlet implementation class DisplayAllProjects
 */
@WebServlet("/DisplayAllProjects")
public class DisplayAllProjects extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		try {
			// get the data
			Projects prjs = new Projects();
			
			request.setAttribute( "prjs", prjs );
			request.getRequestDispatcher( "/WEB-INF/DisplayAllProjects.jsp" ).forward(
					request, response );
		} catch (SQLException e) {
			throw new ServletException( e );
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
