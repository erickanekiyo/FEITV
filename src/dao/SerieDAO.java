/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author ekpri
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import model.Serie;

public class SerieDAO {
    private Connection conn;
    
    public SerieDAO(Connection conn) {
        this.conn = conn;
    }
    
    
    private Serie buildSerie(ResultSet result) throws SQLException {
        Serie serie = new Serie();
        serie.setId(result.getInt("video.id"));
        serie.setTitle(result.getString("video.title"));
        serie.setThumb(result.getString("video.thumb"));
        serie.setDuration(result.getInt("video.duration"));
        serie.setDataUp(result.getTimestamp("video.data_up"));
        serie.setDescription(result.getString("video.description"));
        serie.setEpisode(result.getInt("serie.episode"));
        serie.setTotalEpisodes(result.getInt("serie.total_episodes"));
        serie.setCurrentState(result.getString("serie.current_state"));
        return serie;
    }
    
    public List<Serie> listSerie() throws SQLException {
        List<Serie> list = new ArrayList<>();
        String sql = "SELECT video.*, serie.episode, serie.total_episodes, "
                     + "serie.current_state "
                     + "FROM tbvideos video "
                     + "JOIN tbseries serie ON video.id = serie.id "
                     + "ORDER BY video.data_up DESC";

        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                list.add(buildSerie(result));
            }
        }
        return list;
    }
    
    public void insert(Serie serie) throws SQLException {
        
        String sqlVideo = "INSERT INTO tbvideos (title, thumb, duration, "
                          + "description) "
                          + "VALUES (?, ?, ?, ?) "
                          + "RETURNING id";
        
        try (PreparedStatement statementVideo = conn.prepareStatement(sqlVideo)) {
            statementVideo.setString(1, serie.getTitle());
            statementVideo.setString(2, serie.getThumb());
            statementVideo.setInt(3, serie.getDuration());
            statementVideo.setString(4, serie.getDescription());
            
            try (ResultSet result = statementVideo.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String sqlSerie = "INSERT INTO tbseries (id, episode, "
                                      + "total_episodes, current_state) "
                                      + "VALUES (?, ?, ?, ?)";
                    
                    try (PreparedStatement statementSerie = 
                        conn.prepareStatement(sqlSerie)) {
                        
                        statementSerie.setInt(1, id);
                        statementSerie.setInt(2, serie.getEpisode());
                        statementSerie.setInt(3, serie.getTotalEpisodes());
                        statementSerie.setString(4, serie.getCurrentState());
                        statementSerie.executeUpdate();
                    }
                }
            }
        }
    }
    
    public void updateEpisode(int idSerie, int episode) throws SQLException {
        String sql = "UPDATE tbseries SET episode = ? WHERE id = ?";
        
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, episode);
            statement.setInt(2, idSerie);
            statement.executeUpdate();
        }
    }
    
    public void updateState(int idSerie, String state) throws SQLException {
        String sql = "UPDATE tbseries SET current_state = ? WHERE id = ?";
        
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, state);
            statement.setInt(2, idSerie);
            statement.executeUpdate();
        }
    }
}