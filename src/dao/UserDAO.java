/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import model.User;

/**
 *
 * @author ekpri
 */
public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }
    
    public ResultSet check(User user) throws SQLException{
        String sql = "select * from tbusuarios "
                + "where name = ? and password = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(2, user.getPassword());
        statement.execute();
        
        ResultSet result = statement.getResultSet();
        return result;
    }
    
    public void insert(User user) throws SQLException{
        String sql = "insert into tbusuarios (name, password, gender, age) "
                     + "values ('"
                     + user.getName() + "', '"
                     + user.getPassword() + "', '"
                     + user.getGender() + "', '"
                     + user.getAge() + "')";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.execute();
        conn.close();
    }
    
    public void update(User user) throws SQLException{
        String sql = "update tbusuarios set password = ? where name = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, user.getPassword());
        statement.setString(2, user.getName());
        statement.execute();
        conn.close();
    }
    
    public void delete(User user) throws SQLException{
        String sql = "delete from tbusuarios where name = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.execute();
        conn.close();
    }
}

