package com.ayman.MessagesSystem.Code.Dao;

import com.ayman.MessagesSystem.Code.VO.messagesVo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessagesDao extends Dao {

    private static Connection conn = getConn();
    private static int numOfMessages = 0;

    public static void offCon() throws SQLException {
        conn.close();
    }

    public static void onConn() throws SQLException {
        conn = getConn();
    }

    public static String getNameById(int id) throws SQLException {
        String sql = "select users.USERNAME from users inner join messages on messages.FROM_USER=users.ID limit 1";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("USERNAME");
        }
        ps.close();
        rs.close();
        return null;
    }

    public static List<String> getNames() throws SQLException, ClassNotFoundException {
        List<String> list = new ArrayList<String>();
        String sql = "SELECT `USERNAME` FROM `users` WHERE `ONLINE`='1'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(rs.getString("USERNAME"));
        }
        ps.close();
        rs.close();
        return list;
    }

    public static List<messagesVo> getMessage() throws ClassNotFoundException, SQLException {
        List<messagesVo> list = new ArrayList<messagesVo>();
        String sql = "select messages.ID, messages.FROM_USER, messages.MESSAGE_BODY from messages inner join users on users.ID=messages.FROM_USER where 1 ORDER BY messages.ID";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new messagesVo(rs.getInt("ID"), rs.getInt("FROM_USER"), rs.getString("MESSAGE_BODY")));
        };
        ps.close();
        rs.close();
        return list;
    }

    public void insert(String message, String from) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO `messages`(`FROM_USER`, `MESSAGE_BODY`) VALUES (?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, getId(from));
        ps.setString(2, message);
        ps.executeUpdate();
    }
    public static void insertGloablMessage(String message) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO `messages`(`FROM_USER`, `MESSAGE_BODY`) VALUES (1,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, message);
        ps.executeUpdate();
    }
    public static messagesVo updateMessage(messagesVo mv) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE messages SET `MESSAGE_BODY`=? WHERE `ID`=? limit 1";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, mv.getMessage_body().get());
        ps.setInt(2, mv.getId().get());
        ps.executeUpdate();
        return mv;
    }
    public static void deleteMessage(int id) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM messages WHERE `ID`=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
    
    public static void resetMessages() throws SQLException{
        String sql = "DELETE FROM messages WHERE 1";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.executeUpdate();

        Statement s = conn.createStatement();
        s.execute("ALTER TABLE messages AUTO_INCREMENT=1");
    }

    public int getId(String username) throws ClassNotFoundException, SQLException {
        String sql = "SELECT `ID` FROM `users` WHERE `USERNAME`=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            return rs.getInt("ID");
        }
        return -1;
    }

    public static String getUsername(int id) {
        try {
            String sql = "SELECT `USERNAME` FROM `users` WHERE `id`=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("USERNAME");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessagesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static int getNumOfMessages() throws SQLException {
        String sql = "SELECT `ID` FROM `messages` WHERE 1";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            numOfMessages++;
        }
        return numOfMessages;
    }

    public static void resetNum() {
        numOfMessages = 0;
    }
     public static int getMessageId(String message) {
        int id = -1;
        PreparedStatement ps = null;
        try {
            String sql = "SELECT `ID` FROM `messages` WHERE `MESSAGE_BODY`=? limit 1";
            ps = conn.prepareStatement(sql);
            ps.setString(1, message);
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
}
