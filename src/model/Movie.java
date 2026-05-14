/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author ekpri
 */
public class Movie extends Video{
    private String director, genre;

    public Movie() {
    }

    public Movie(String director, String genre, String title, String thumb, 
        String description, int duration, boolean likeState, Timestamp dataUp) {
        
        super(title, thumb, description, duration, likeState, dataUp);
        this.director = director;
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
}
