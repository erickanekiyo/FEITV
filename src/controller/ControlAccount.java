/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.User;
import view.ProfilePanel;
/**
 *
 * @author ekpri
 */
public class ControlAccount {
    private ProfilePanel view;
    private User user;
    
    public ControlAccount(ProfilePanel view, User user){
        this.view = view;
        this.user = user;
    }
    
    public User callModify(){
        return user;
    }
    
    public User callExclude(){
        return user;
    }
}
