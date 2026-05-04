/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import feitv.User;

/**
 *
 * @author ekpri
 */
public class UserDAO {
    private Connect conn;

    public UserDAO(Connect conn) {
        this.conn = conn;
    }
    
    public ResultSet consultar(User user) throws SQLException{
        String sql = "select * from tbusuarios "
                + "where name = ? and password = ? and gender = ? and age = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getGender());
        statement.setInt(4, user.getAge());
        statement.execute();
        
        ResultSet result = statement.getResultSet();
        return result;
    }
}
