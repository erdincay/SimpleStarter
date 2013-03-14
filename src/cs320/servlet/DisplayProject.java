package cs320.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs320.api.HtmlAPI;
import cs320.model.ProjectD;
import cs320.model.UserD;
import cs320.pattern.FactoryF;

/**
 * Servlet implementation class DisplayProject
 */
@WebServlet("/DisplayProject")
public class DisplayProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayProject() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private List<Object> valid(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		// get the user input
        String strID = request.getParameter( HtmlAPI.getIdAttrName() );
        if(strID == null || strID.trim().length() == 0) {
        	response.sendRedirect("DisplayAllProjects");
        	return null;
        }
        // get prj
        long id;
		try {
			id = Long.parseLong(strID);
		} catch (NumberFormatException e) {
        	response.sendRedirect("DisplayAllProjects");
        	return null;
		}
		ProjectD prj = FactoryF.getProjects().getProjectByID(id);
		if (prj == null) {
			response.sendRedirect("DisplayAllProjects");
        	return null;
		}

		boolean nosponsored = true;
		String strUsr = HtmlAPI.getUsrFromSession(request);
		if (strUsr != null) {
			UserD usr = FactoryF.getUsers().getUserByName(strUsr);
			//check if the usr have already been the sponsor to this project
			nosponsored = !prj.checkPledgeByUser(usr);
		}
        
        ArrayList<Object> ret = new ArrayList<Object>();
        ret.add(prj);
        ret.add(nosponsored);
        
		return ret;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Object> ret = null;
		try {
			ret = valid(request,response);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		
		if(ret == null) {
			return;
		}
		
        ProjectD prj = (ProjectD) ret.get(0);
        boolean nosponsored = (boolean) ret.get(1);
        
        request.setAttribute( "prj", prj );
        request.setAttribute("nosponsored", nosponsored);
		request.getRequestDispatcher( "/WEB-INF/DisplayProject.jsp" ).forward(
				request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
