/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import model.User;
import view.MenuPanel;
import view.ModifyAccountPanel;
import view.ProfilePanel;
/**
 *
 * @author ekpri
 */
public class ControlAccount {
    private ProfilePanel view;
    private User user;
    private Connection conn;
    private MenuPanel menuPnl;
    
    public ControlAccount(ProfilePanel view, User user, Connection conn, 
            MenuPanel menuPnl){
        this.view = view;
        this.user = user;
        this.conn = conn;
        this.menuPnl = menuPnl;
    }
    
    public void callModify(User user, Connection conn){
        ModifyAccountPanel modifyPnl = new ModifyAccountPanel(user, conn, menuPnl);
        modifyPnl.setVisible(true);
        
        this.view.dispose();
    }
    
    public User callExclude(){
        return user;
    }
}
