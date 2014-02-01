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
import cs320.model.PledgeD;
import cs320.model.UserD;
import cs320.pattern.FactoryF;

/**
 * Servlet implementation class Sponsor
 */
@WebServlet("/Sponsor")
public class Sponsor extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String fstrAmount = "amount";
    private static final String fstrReward = "reward";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sponsor() {
        super();
    }

    private List<Object> checkValid(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
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

        //get the users data
        UserD usr = FactoryF.getUsers().getUserByName(strUsr);
        if (usr == null) {
            response.sendRedirect("DisplayProject?" + HtmlAPI.getIdAttrName() + "=" + strID);
            return null;
        }

        //check if the usr have already been the sponsor to this project
        if (prj.checkPledgeByUser(usr)) {
            response.sendRedirect("DisplayProject?" + HtmlAPI.getIdAttrName() + "=" + strID);
            return null;
        }

        ArrayList<Object> ret = new ArrayList<Object>();
        ret.add(prj);
        ret.add(usr);

        return ret;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Object> ret = null;
        try {
            ret = checkValid(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        if (ret == null) {
            return;
        }
        ProjectD prj = (ProjectD) ret.get(0);

        request.setAttribute("prj", prj);
        request.getRequestDispatcher("/WEB-INF/Sponsor.jsp").forward(
                request, response);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Object> ret = null;
        try {
            ret = checkValid(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        if (ret == null) {
            return;
        }

        ProjectD prj = (ProjectD) ret.get(0);
        UserD usr = (UserD) ret.get(1);

        // get the user input
        String strAmount = request.getParameter(fstrAmount);
        String strReward = request.getParameter(fstrReward);
        String submit = request.getParameter(HtmlAPI.getSubmitAttrName());

        if (submit.equals(HtmlAPI.getCancelAttrName())) {
            response.sendRedirect("DisplayProject?" + HtmlAPI.getIdAttrName() + "=" + prj.getIdentity());
            return;
        }

        if ((strAmount == null || strAmount.trim().length() == 0)) {
            response.sendRedirect("Sponsor?" + HtmlAPI.getIdAttrName() + "=" + prj.getIdentity());
            return;
        }

        //translate input
        int pledgedAmount;
        try {
            pledgedAmount = Integer.parseInt(strAmount);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("Sponsor?" + HtmlAPI.getIdAttrName() + "=" + prj.getIdentity());
            return;
        }

        int rewardAmount = 0;
        if (strReward != null && strReward.trim().length() != 0) {
            try {
                rewardAmount = Integer.parseInt(strReward);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        /**
         * the amount the usr pledged should higher than the amount of the selected reward.
         * that should be validated on the client side.
         * on the server side, if the reward is invalid to according amount,
         * the server just change the usr's reward to the most close one. 
         */
        if (rewardAmount > pledgedAmount) {
            rewardAmount = pledgedAmount;
        }
        PledgeD plg = new PledgeD(pledgedAmount, rewardAmount);
        prj.addPledged(usr, plg);

        response.sendRedirect("DisplayProject?" + HtmlAPI.getIdAttrName() + "=" + prj.getIdentity());
    }

}
