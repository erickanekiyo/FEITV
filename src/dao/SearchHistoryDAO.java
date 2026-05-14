/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchHistoryDAO {
    private Connection conn;

    public SearchHistoryDAO(Connection conn) {
        this.conn = conn;
    }
    
    //Return words from searched history
    public List<String> getHistory(int idUser) throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT search_term FROM tbsearch_history "
                     + "WHERE id_user = ? "
                     + "ORDER BY MAX(searched_at) DESC "
                     + "GROUP BY search_term "
                     + "LIMIT 10";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    list.add(result.getString("search_term"));
                }
            }
        }
        return list;
    }
    
    public void saveSearch(int idUser, String word) throws SQLException {
        //Verify if already was searched last time
        if (!isLastSearch(idUser, word)) {
            String sql = "INSERT INTO tbsearch_history (id_user, search_term) "
                         + "VALUES (?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, idUser);
                statement.setString(2, word);
                statement.executeUpdate();
            }
        }
    }
    
    private boolean isLastSearch(int idUser, String word) throws SQLException {
        String sql = "SELECT search_term FROM tbsearch_history "
                     + "WHERE id_user = ? "
                     + "ORDER BY searched_at DESC LIMIT 1";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getString("search_term").equalsIgnoreCase(word);
                }
            }
        }
        return false;
    }
}
