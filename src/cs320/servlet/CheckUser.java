package cs320.servlet;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cs320.api.HtmlAPI;
import cs320.pattern.FactoryF;

/**
 * Servlet implementation class ChechkUser
 */
@WebServlet("/CheckUser")
public class CheckUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strUsr = request.getParameter(HtmlAPI.getUsrAttrName());
				
		Map<String, String> options = new LinkedHashMap<String, String>();
		if(strUsr == null) {
			options.put(strUsr, (new Boolean(false)).toString());
		}
		else {
			options.put(strUsr, ((Boolean)(FactoryF.getUsers().getUserByName(strUsr) != null)).toString());
		}
		
		String json = new Gson().toJson(options);

		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
