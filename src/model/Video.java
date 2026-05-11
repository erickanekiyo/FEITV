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
    private int id;
    private String title;
    private String thumb;
    private int duration;
    private Timestamp dataUp;

    public Video() {
    }

    public Video(String title, String thumb, int duration, Timestamp dataUp) {
        this.title = title;
        this.thumb = thumb;
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
