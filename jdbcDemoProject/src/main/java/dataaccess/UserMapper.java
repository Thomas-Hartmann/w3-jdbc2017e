/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import static dataaccess.DB.getConnection;
import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tha
 */
public class UserMapper {
    public List<User> getAllUsers(){
        List<User> users = new ArrayList();
        try {
            String sql = "SELECT * FROM usertable2";
            Connection connection = DB.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
//                System.out.println(rs.getString("username"));
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                User user = new User(id, username, password);
                users.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }
    //public void addUser(User user){}
    public boolean validateUser(String username, String password){
        try {
            
            
            String sql = "SELECT username, password FROM usertable2 WHERE username = ?";
            PreparedStatement pstmt = DB.getConnection().prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                String pwd = rs.getString("password");
                if(pwd.equals(password)){
                    return true;
                }
            } else {
                return false;
            }
        
        
        
        } catch (SQLException ex) {
            Logger.getLogger(UserMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public static void main(String[] args) {
        UserMapper um = new UserMapper();
        boolean result1 = um.validateUser("admin", "password123");
        boolean result2 = um.validateUser("admin", "password1234");
        System.out.println("result1: "+result1);
        System.out.println("result2: "+result2);
    }
}
