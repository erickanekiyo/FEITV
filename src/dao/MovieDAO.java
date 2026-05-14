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
import model.Movie;

public class MovieDAO {
    private Connection conn;

    public MovieDAO(Connection conn) {
        this.conn = conn;
    }
    
    
    private Movie buildFilme(ResultSet result) throws SQLException {
        Movie movie = new Movie();
        movie.setId(result.getInt("video.id"));
        movie.setThumb(result.getString("video.thumb"));
        movie.setTitle(result.getString("video.title"));
        movie.setDuration(result.getInt("video.duration"));
        movie.setDataUp(result.getTimestamp("video.data_up"));
        movie.setDescription(result.getString("video.description"));
        movie.setDirector(result.getString("movie.director"));
        movie.setGenre(result.getString("movie.genre"));
        return movie;
    }
    
    public List<Movie> listFilmes() throws SQLException {
        List<Movie> list = new ArrayList<>();
        String sql = "SELECT video.*, movie.director, movie.genre "
                     + "FROM tbvideos video "
                     + "JOIN tbmovies movie ON video.id = movie.id "
                     + "ORDER BY video.data_up DESC";

        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                list.add(buildFilme(result));
            }
        }
        return list;
    }
    
    public void insert(Movie movie) throws SQLException {
        String sqlVideo = "INSERT "
                          + "INTO tbvideos (title, thumb, duration, description) "
                          + "VALUES (?, ?, ?, ?) RETURNING id";

        try (PreparedStatement statementVideo = conn.prepareStatement(sqlVideo)) {
            statementVideo.setString(1, movie.getTitle());
            statementVideo.setString(2, movie.getThumb());
            statementVideo.setInt(3, movie.getDuration());
            statementVideo.setString(4, movie.getDescription());
            
            try (ResultSet result = statementVideo.executeQuery()) {
                if (result.next()) {
                    int idGerado = result.getInt("id");
                    String sqlMovie = "INSERT INTO tbmovies (id, director, genre) "
                                    + "VALUES (?, ?, ?)";
                    
                    try (PreparedStatement statementMovie = 
                         conn.prepareStatement(sqlMovie)) {
                        
                        statementMovie.setInt(1, idGerado);
                        statementMovie.setString(2, movie.getDirector());
                        statementMovie.setString(3, movie.getGenre());
                        statementMovie.executeUpdate();
                    }
                }
            }
        }
    }
}
