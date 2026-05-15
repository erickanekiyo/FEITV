/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.SQLException;
import dao.VideoDAO;
import model.Serie;
import model.User;
/**
 *
 * @author ekpri
 */
public class ControlSerieInfo {
    private VideoDAO dao;

    public ControlSerieInfo(Connection conn) {
        this.dao = new VideoDAO(conn);
    }

    public void markAsWatched(User user, int episodeId) throws SQLException {
        dao.setEpisodeWatched(user.getId(), episodeId);
    }
    
    public int calculateProgress(int watchedCount, int totalEpisodes) {
        if (totalEpisodes <= 0) return 0;
        return (watchedCount * 100) / totalEpisodes;
    }
    
    public int getWatchedCount(User user, Serie serie) throws SQLException {
        return dao.countWatchedEpisodes(user.getId(), serie.getId());
    }
    
    public boolean toggleLike(User user, Serie serie) throws SQLException {
        boolean currentlyLiked = dao.isLiked(user.getId(), serie.getId());
        if (currentlyLiked) {
            dao.unlikeVideo(user.getId(), serie.getId());
        } else {
            dao.likeVideo(user.getId(), serie.getId());
        }
        return !currentlyLiked;
    }

    public void addSerieToList(User user, Serie serie) throws SQLException {
        dao.addToList(user.getId(), serie.getId());
    }
    
    public void removeSerieFromList(User user, Serie serie) throws SQLException {
        dao.removeFromList(user.getId(), serie.getId());
    }
    
    public boolean toggleFavorite(User user, Serie serie) throws SQLException {
        boolean isFav = checkIfFavorite(user, serie);
        if (isFav) {
            removeSerieFromList(user, serie);
        } else {
            addSerieToList(user, serie);
        }
        return !isFav;
    }
    
    public boolean checkIfLiked(User user, Serie serie) throws SQLException {
        return dao.isLiked(user.getId(), serie.getId());
    }
    
    public boolean checkIfFavorite(User user, Serie serie) throws SQLException {
    return dao.isVideoInList(user.getId(), serie.getId());
}
}
