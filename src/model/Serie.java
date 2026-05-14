/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ekpri
 */

public class Serie extends Video implements State {
    private int episode, totalEpisodes;
    private String currentState;

    public Serie() {
        this.currentState = State.NOT_STARTED;
    }
    
    @Override
    public String getCurrentState() {
        return currentState;
    }

    @Override
    public void setCurrentState(String state) {
        this.currentState = state;
    }

    @Override
    public boolean isFinished() {
        return this.currentState.equals(State.FINISHED);
    }

    @Override
    public boolean isWatching() {
        return this.currentState.equals(State.WATCHING);
    }

    
    //GETTERS & SETTERS
    public int getEpisode() {
        return episode;
    }
    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public int getTotalEpisodes() {
        return totalEpisodes;
    }
    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }
}
