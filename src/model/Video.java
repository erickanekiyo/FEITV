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
public class Video {
    private boolean likeState;
    private int duration;
    private int id;
    private String thumb;
    private String title;
    private String description;
    private Timestamp dataUp;

    public Video() {
    }

    public Video(String title, String thumb, String description, int duration, 
                                          boolean likeState, Timestamp dataUp) {
        this.title = title;
        this.thumb = thumb;
        this.likeState = false;
        this.description = description;
        this.duration = duration;
        this.dataUp = dataUp;
    }
    
    //GETTERS & SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLikeState() {
        return likeState;
    }

    public void setLikeState(boolean likeState) {
        this.likeState = likeState;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Timestamp getDataUp() {
        return dataUp;
    }

    public void setDataUp(Timestamp dataUp) {
        this.dataUp = dataUp;
    }
    
}
