/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
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
        String sql = "select * from tbusers where name = ? and password = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(2, user.getPassword());
        statement.execute();
        
        ResultSet result = statement.getResultSet();
        return result;
    }
    
    public void insert(User user) throws SQLException{
        String sql = "INSERT INTO tbusers (name, password, gender, age) "
                     + "VALUES (?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getGender());
        statement.setInt(4, user.getAge());
        statement.executeUpdate();
    }
    
    public void update(User user) throws SQLException{
        String sql = "UPDATE tbusers SET name = ?, password = ? WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(2, user.getPassword());
        statement.setInt(3, user.getId());
        statement.execute();
    }
    
    public void delete(User user) throws SQLException{
        String sql = "delete from tbusers where name = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.execute();
        conn.close();
    }
}

