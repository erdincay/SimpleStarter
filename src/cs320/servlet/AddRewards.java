package cs320.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs320.api.HtmlAPI;
import cs320.model.ProjectD;
import cs320.model.RewardD;
import cs320.pattern.FactoryF;

/**
 * Servlet implementation class AddRewards
 */
@WebServlet("/AddRewards")
public class AddRewards extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String fstrAmount = "amount";
    private static final String fstrDescription = "description";
    private static final String fstrAdd = "Add";
    private static final String fstrFinish = "Finish";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddRewards() {
        super();
        // TODO Auto-generated constructor stub
    }

    private ProjectD checkValid(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        // check if a user has logged in or not
        String strUsr = HtmlAPI.getUsrFromSession(request);
        if (strUsr == null) {
            response.sendRedirect("Login");
            return null;
        }
        // get the user input
        String strID = request.getParameter(HtmlAPI.getIdAttrName());
        if (strID == null || strID.trim().length() == 0) {
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
        if (!prj.getAuthorName().equals(strUsr)) {
            response.sendRedirect("DisplayProject?" + HtmlAPI.getIdAttrName() + "=" + strID);
            return null;
        }
        return prj;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectD prj = null;
        try {
            prj = checkValid(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        if (prj == null) {
            return;
        }

        request.setAttribute("prj", prj);
        request.getRequestDispatcher("/WEB-INF/AddRewards.jsp").forward(
                request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectD prj = null;
        try {
            prj = checkValid(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        if (prj == null) {
            return;
        }

        // get the user input
        String strAmount = request.getParameter(fstrAmount);
        String description = request.getParameter(fstrDescription);
        String submit = request.getParameter(HtmlAPI.getSubmitAttrName());

        if (!(strAmount == null || strAmount.trim().length() == 0
                || description == null || description.trim().length() == 0)) {
            //translate input
            int amount;
            try {
                amount = Integer.parseInt(strAmount);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("AddRewards?" + HtmlAPI.getIdAttrName() + "=" + prj.getIdentity());
                return;
            }

            // create the new reward and add to project
            RewardD rd = new RewardD(amount, description);
            prj.getRewards().saveReward(rd);
        }
        // send the user to add another rewards or show the project
        if (submit.equals(fstrAdd)) {
            response.sendRedirect("AddRewards?" + HtmlAPI.getIdAttrName() + "=" + prj.getIdentity());
        } else if (submit.equals(fstrFinish)) {
            response.sendRedirect("DisplayProject?" + HtmlAPI.getIdAttrName() + "=" + prj.getIdentity());
        }
    }

}
