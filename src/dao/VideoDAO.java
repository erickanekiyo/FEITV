/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import model.Video;

/**
 *
 * @author ekpri
 */
public class VideoDAO {
    private Connection conn;

    public VideoDAO(Connection conn) {
        this.conn = conn;
    }
    
    public List<Video> listVideos() throws SQLException {
        List<Video> list = new ArrayList<>();
        String sql = "SELECT * FROM tbvideos ORDER BY data_up DESC";
        
        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Video video = new Video();
                video.setId(result.getInt("id"));
                video.setTitle(result.getString("title"));
                video.setThumb(result.getString("thumb"));
                video.setDuration(result.getInt("duration"));
                video.setDataUp(result.getTimestamp("data_up"));
                list.add(video);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", 
                                          JOptionPane.ERROR_MESSAGE);
        }
        
        return list;
    }
}
