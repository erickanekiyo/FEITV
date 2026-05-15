/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.VideoDAO;
import java.sql.Connection;
import java.sql.SQLException;
import model.Movie;
import model.User;
/**
 *
 * @author ekpri
 */
public class ControlMovieInfo {
    private Connection conn;
    private VideoDAO dao;

    public ControlMovieInfo(Connection conn) {
        this.conn = conn;
        this.dao = new VideoDAO(conn);
    }

    // Formatação de tempo é lógica, melhor ficar aqui
    public String formatDuration(int totalSeconds) {
        int hour = totalSeconds / 3600;
        int min = (totalSeconds % 3600) / 60;
        int sec = totalSeconds % 60;
        return hour <= 0 
            ? String.format("%02d:%02d", min, sec) 
            : String.format("%02d:%02d:%02d", hour, min, sec);
    }

    public boolean checkIfLiked(User user, Movie movie) throws SQLException {
        return dao.isLiked(user.getId(), movie.getId());
    }

    // Lógica de "Toggle" (Inverter o estado do like)
    public boolean toggleLike(User user, Movie movie) throws SQLException {
        boolean currentlyLiked = dao.isLiked(user.getId(), movie.getId());
        if (currentlyLiked) {
            dao.unlikeVideo(user.getId(), movie.getId());
        } else {
            dao.likeVideo(user.getId(), movie.getId());
        }
        return !currentlyLiked;
    }

    public void addMovieToList(User user, Movie movie) throws SQLException {
        dao.addToList(user.getId(), movie.getId());
    }
}
