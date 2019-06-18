package com.ayman.MessagesSystem.Code.Dao;

import com.ayman.MessagesSystem.Code.VO.userVo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersDao extends Dao implements DaoList<userVo> {

    private static UsersDao user = null;
    private static Connection conn = null;
    private static int numOfUsers = 0;
    private static int numOfOnline = 0;
    private static int numOfOffline = 0;

    public UsersDao() {

    }
    public static void offConn() throws SQLException{
        conn.close();
    }
    public static void onConn() throws SQLException{
        conn = getConn();
    }

    public static UsersDao getInstance() {
        if (user == null) {
            user = new UsersDao();
        }
        return user;
    }

    @Override
    public boolean insert(userVo t) {
        boolean flag = false;
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO `users`(`USERNAME`, `PASSWORD`, `EMAIL`, `TYPE`, `ONLINE`) VALUES (?,?,?,2,'0')";
            ps = conn.prepareStatement(sql);
            ps.setString(1, t.getUsername().get());
            ps.setString(2, t.getPassword().get());
            ps.setString(3, t.getEmail().get());
            flag = (ps.executeUpdate() >= 1);
        } catch (SQLException ex) {
            Logger.getLogger(UsersDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public static boolean userInsertFromTable(userVo t) throws SQLException {
        boolean flag = false;
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO `users`(`USERNAME`, `PASSWORD`, `EMAIL`, `TYPE`, `ONLINE`) VALUES (?,?,?,?,'0')";
            ps = conn.prepareStatement(sql);
            ps.setString(1, t.getUsername().get());
            ps.setString(2, t.getPassword().get());
            ps.setString(3, t.getEmail().get());
            ps.setInt(4, t.getUserTypeInt().get());
            flag = (ps.executeUpdate() >= 1);
        } catch (SQLException ex) {
            Logger.getLogger(UsersDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    @Override
    public userVo update(userVo t, int id) {
        try {
            String sql = "UPDATE `users` SET `USERNAME`=?,`PASSWORD`=?,`EMAIL`=?,`TYPE`=?,`ONLINE`=? WHERE `ID`=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, t.getUsername().get());
            ps.setString(2, t.getPassword().get());
            ps.setString(3, t.getEmail().get());
            ps.setInt(4, t.getUserTypeInt().get());
            ps.setString(5, String.valueOf(t.getOnline().get()));
            ps.setInt(6, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    }

    @Override
    public boolean delete(int t) throws SQLException {
        boolean flag = false;
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM `users` WHERE `ID`=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, t);
            flag = (ps.executeUpdate() >= 1);
        } catch (SQLException ex) {
            Logger.getLogger(UsersDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public void setOnline(String username, String online) {
        try {
            String sql = "UPDATE `users` SET `ONLINE`=? WHERE `USERNAME`=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, online);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteMessages(int id) {
        try {
            String sql = "DELETE FROM `messages` WHERE FROM_USER=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<userVo> loadAll() {
        List<userVo> list = new ArrayList<>();
        PreparedStatement ps = null;
        try {
            String sql = "SELECT `ID`, `USERNAME`, `PASSWORD`, `EMAIL`, `TYPE`, `ONLINE` FROM `users` WHERE 1";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new userVo(rs.getInt("ID"), rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getString("EMAIL"), rs.getInt("TYPE"), rs.getString("ONLINE")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static int getId(String name) {
        int id = -1;
        PreparedStatement ps = null;
        try {
            String sql = "SELECT `ID` FROM `users` WHERE `USERNAME`=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("ID");
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public static int getNumOfUsers() {
        PreparedStatement ps = null;
        try {
            String sql = "SELECT `ID` FROM `users` WHERE 1";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                numOfUsers++;
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numOfUsers;
    }

    public static int getNumOfOnline() {
        PreparedStatement ps = null;
        try {
            String sql = "SELECT `ID` FROM `users` WHERE `ONLINE`='1'";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                numOfOnline++;
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numOfOnline;
    }

    public static int getNumOfOffline() {
        PreparedStatement ps = null;
        try {
            String sql = "SELECT `ID` FROM `users` WHERE `ONLINE`='0'";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                numOfOffline++;
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numOfOffline;
    }

    public static void resetNum() {
        numOfUsers = 0;
        numOfOnline = 0;
        numOfOffline = 0;
    }
}
