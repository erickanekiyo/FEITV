/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author ekpri
 */
public class Connect {
    public Connection getConnection() throws SQLException{
        Connection connect = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5433/usuarios",
            "postgres", "Botato20-");
        return connect;
    }
}
