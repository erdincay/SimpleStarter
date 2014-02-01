package cs320.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import cs320.pattern.FactorySqlF;

public class PledgesSqlC implements PledgesI {

    private long projectId;

    public PledgesSqlC(long id) {
        this.projectId = id;
    }

    private long getProjectId() {
        return projectId;
    }

    public int totalPledges() {

        int total = 0;

        try {
            Connection conn = FactorySqlF.getDbConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select pledeged_amount from pledges where project_id = " + getProjectId() + " group by user_id");

            total = 0;
            while (rs.next()) {
                total += rs.getInt("pledeged_amount");
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    public boolean checkPledgeByUser(String username) {
        boolean ret = false;

        try {
            Connection conn = FactorySqlF.getDbConnection();
            String sql = "select 1 from pledges where project_id = ? and user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, getProjectId());
            pstmt.setInt(2, FactorySqlF.getUsers().getUserByName(username).getId());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = true;
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public HashMap<String, PledgeD> generatePledges() {

        HashMap<String, PledgeD> map = new HashMap<String, PledgeD>();
        try {
            Connection conn = FactorySqlF.getDbConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from pledges where project_id = " + getProjectId() + " group by user_id");

            while (rs.next()) {
                int pledeged_amount = rs.getInt("pledeged_amount");
                int reward_amount = rs.getInt("reward_amount");
                int user_id = rs.getInt("user_id");
                UserD usr = ((UsersSqlC) FactorySqlF.getUsers()).getUserByID(user_id);
                if (usr != null) {
                    map.put(usr.getName(), new PledgeD(pledeged_amount, reward_amount));
                }
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return map;
    }

    public void savePledge(UserD usr, PledgeD pl) {

        try {
            Connection conn = FactorySqlF.getDbConnection();
            String sql = "insert into pledges (`pledeged_amount`, `reward_amount`, `user_id`, `project_id`) values (?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pl.getPledged());
            pstmt.setInt(2, pl.getRewardAmount());
            pstmt.setInt(3, usr.getId());
            pstmt.setLong(4, this.getProjectId());

            pstmt.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
