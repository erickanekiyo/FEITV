/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ekpri
 */
//Defines a contract that the object must return values for all the functions
public interface State {
    String WATCHING = "Watching", FINISHED = "Finished", 
           NOT_STARTED = "Not Started";

    String getCurrentState();

    void setCurrentState(String state);

    boolean isFinished();

    boolean isWatching();
}
