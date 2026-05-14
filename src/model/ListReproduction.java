/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ekpri
 */
import java.util.ArrayList;
import java.util.List;

public class ListReproduction {
    private int id;
    private String name;
    private List<Video> videos;
    
    public ListReproduction() {
        this.videos = new ArrayList<>();
    }
    
    public void addVideo(Video video) {
        this.videos.add(video);
    }
    
    public void removeVideo(Video video) {
        this.videos.remove(video);
    }
    

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Video> getVideos() {
        return videos;
    }
    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
    
    
}
