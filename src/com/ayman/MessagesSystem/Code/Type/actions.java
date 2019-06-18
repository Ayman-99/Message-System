package com.ayman.MessagesSystem.Code.Type;

import com.ayman.MessagesSystem.Code.Dao.Dao;
import com.ayman.MessagesSystem.Code.Dao.UsersDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class actions {
    
    public static void setOnline(String username, String online){
        UsersDao.getInstance().setOnline(username, online);
    }
    
    public static void deleteMessages(int id){
        UsersDao.getInstance().deleteMessages(id);
    }
    
    public static int getUserType(String username) throws SQLException{
        String sql = "SELECT `TYPE` FROM `users` WHERE `USERNAME`=?";
        Connection conn = Dao.getConn();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return rs.getInt("TYPE");
        }
        return -1;
    }
    
}
