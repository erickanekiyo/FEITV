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
import model.Movie;
import model.Serie;
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
    
    //Constructor to build Movie object 
    private Movie buildMovie(ResultSet result) throws SQLException {
        Movie movie = new Movie();
        movie.setId(result.getInt("id"));
        movie.setThumb(result.getString("thumb"));
        movie.setTitle(result.getString("title"));
        movie.setDuration(result.getInt("duration"));
        movie.setDataUp(result.getTimestamp("data_up"));
        movie.setDescription(result.getString("description"));
        return movie;
    }
    
    //Constructor to build Serie object 
    private Serie buildSerie(ResultSet result) throws SQLException {
        Serie serie = new Serie();
        serie.setId(result.getInt("id"));
        serie.setThumb(result.getString("thumb"));
        serie.setTitle(result.getString("title"));
        serie.setDuration(result.getInt("duration"));
        serie.setDataUp(result.getTimestamp("data_up"));
        serie.setDescription(result.getString("description"));
        serie.setTotalEpisodes(result.getInt("total_episodes"));
        return serie;
    }
    
    public void setEpisodeWatched(int idUser, int idVideo) throws SQLException {
        String sql = "INSERT INTO "
                     + "tbhistory (id_user, id_video, last_watched) "
                     + "VALUES (?, ?, NOW()) ON CONFLICT (id_user, id_video) " 
                     + "DO UPDATE "
                     + "SET watched_count = tbhistory.watched_count + 1";
        
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            statement.setInt(2, idVideo);
            statement.executeUpdate();
        }
    }
    
    public int countWatchedEpisodes(int idUser, int idVideo) throws SQLException {
        String sql = "SELECT watched_count "
                   + "FROM tbhistory WHERE id_user = ? AND id_video = ?";
        
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            statement.setInt(2, idVideo);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) return result.getInt("watched_count");
            }
        }
        return 0;
    }
    
    //List videos from the DATABASE
    public List<Video> listVideos(int idUser) throws SQLException {
        List<Video> list = new ArrayList<>();
        MovieDAO movieDAO = new MovieDAO(conn);
        SerieDAO serieDAO = new SerieDAO(conn);
        
        for (Movie f : movieDAO.listMovie()) {
            f.setLikeState(isLiked(idUser, f.getId()));
            list.add(f);
        }
        for (Serie s : serieDAO.listSerie()) {
            s.setLikeState(isLiked(idUser, s.getId()));
            list.add(s);
        }
        return list;
    }
    
    //List videos in the favorist list of the user
    public List<Video> listFavVideos(int idUser) throws SQLException {
        List<Video> list = new ArrayList<>();
        String sql = "SELECT v.*, "
                     + "CASE WHEN movie.id IS NOT NULL THEN 'movie' "
                     + "ELSE 'serie' END AS type, "
                     + "serie.total_episodes "
                     + "FROM tbvideos video "
                     + "INNER JOIN tblist list ON video.id = list.id_video "
                     + "LEFT JOIN tbmovies movie ON video.id = movie.id "
                     + "LEFT JOIN tbseries serie ON video.id = serie.id "
                     + "WHERE list.id_user = ? "
                     + "ORDER BY list.data_add DESC";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Video video = result.getString("type").equals("movie") 
                                  ? buildMovie(result) : buildSerie(result);
                    
                    video.setLikeState(isLiked(idUser, video.getId()));
                    list.add(video);
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
    
    public boolean isVideoInList(int idUser, int idVideo) throws SQLException {
        String sql = "SELECT 1 FROM tblist WHERE id_user = ? AND id_video = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            statement.setInt(2, idVideo);
            try (ResultSet result = statement.executeQuery()) {
                return result.next(); // Retorna true se encontrar um registro
            }
        }
    }
    
    //Search by title in SQL
    public List<Video> searchByTitle(String title) throws SQLException {
        List<Video> list = new ArrayList<>();
        String sql = "SELECT video.*, "
                     + "serie.total_episodes, "
                     + "CASE WHEN movie.id IS NOT NULL "
                     + "THEN 'movie' ELSE 'serie' END AS type "
                     + "FROM tbvideos video "
                     + "LEFT JOIN tbmovies movie ON video.id = movie.id "
                     + "LEFT JOIN tbseries serie ON video.id = serie.id "
                     + "WHERE video.title ILIKE ? "
                     + "ORDER BY video.data_up DESC";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, "%" + title + "%");
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Video video;
                    if (result.getString("type").equals("movie")) {
                        video = buildMovie(result);
                    } else {
                        video = buildSerie(result);
                    }
                    list.add(video);
                }
            }
        }
        return list;
    }
}
