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
    
    //Constructor to build Video object 
    private Video buildVideo(ResultSet result) throws SQLException {
        Video video = new Video();
        video.setId(result.getInt("id"));
        video.setTitle(result.getString("title"));
        video.setThumb(result.getString("thumb"));
        video.setDuration(result.getInt("duration"));
        video.setDataUp(result.getTimestamp("data_up"));
        video.setDescription(result.getString("description"));
        return video;
    }
    
    //List videos from the DATABASE
    public List<Video> listVideos(int idUser) throws SQLException {
        List<Video> list = new ArrayList<>();
        String sql = "SELECT * FROM tbvideos ORDER BY data_up DESC";

        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Video video = buildVideo(result);
                video.setLikeState(isLiked(idUser, video.getId()));
                list.add(buildVideo(result));
            }
        }
        return list;
    }
    
    //List videos in the favorist list of the user
    public List<Video> listFavVideos(int idUser) throws SQLException {
        List<Video> list = new ArrayList<>();
        String sql = "SELECT video.* FROM tbvideos video "
                     + "JOIN tblist list ON video.id = list.id_video "
                     + "WHERE list.id_user = ? "
                     + "ORDER BY list.data_add DESC";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    list.add(buildVideo(result));
                }
            }
        }
        return list;
    }
    
    //Verify if the video was liked by the user
    public boolean isLiked(int idUser, int idVideo) throws SQLException {
        String sql = "SELECT 1 FROM tblikes WHERE id_user = ? AND id_video = ?";
        
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            statement.setInt(2, idVideo);
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        }
    }

    //Like vídeo
    public void likeVideo(int idUser, int idVideo) throws SQLException {
        String sql = "INSERT INTO tblikes (id_user, id_video) VALUES (?, ?)";
        
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            statement.setInt(2, idVideo);
            statement.executeUpdate();
        }
    }

    //Dislike vídeo
    public void unlikeVideo(int idUser, int idVideo) throws SQLException {
        String sql = "DELETE FROM tblikes WHERE id_user = ? AND id_video = ?";
        
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            statement.setInt(2, idVideo);
            statement.executeUpdate();
        }
    }
    
    //Add vídeo into list
    public void addToList(int idUser, int idVideo) throws SQLException {
        String sql = "INSERT INTO tblist (id_user, id_video) VALUES (?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            statement.setInt(2, idVideo);
            statement.executeUpdate();
        }
    }

    //Remove vídeo of the list
    public void removeFromList(int idUser, int idVideo) throws SQLException {
        String sql = "DELETE FROM tblist WHERE id_user = ? AND id_video = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            statement.setInt(2, idVideo);
            statement.executeUpdate();
        }
    }
    
    //Search video by name inside public list
    public List<Video> searchByTitle(String title) throws SQLException {
        List<Video> list = new ArrayList<>();
        String sql = "SELECT * FROM tbvideos "
                     + "WHERE title ILIKE ? "
                     + "ORDER BY data_up DESC";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, "%" + title + "%");
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    list.add(buildVideo(result));
                }
            }
        }
        return list;
    }
}
