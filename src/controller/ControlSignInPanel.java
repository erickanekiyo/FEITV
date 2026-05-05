/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.User;
import view.SignInPanel;
import view.LoginPanel;
import java.util.ArrayList;

/**
 *
 * @author ekpri
 */
public class ControlSignInPanel {
    private SignInPanel view;
    private ArrayList<User> usuarios = new ArrayList<>();

    public ControlSignInPanel(SignInPanel view) {
        this.view = view;
    }
    
    public void register(){
        String name = this.view.getTxtUsername().getText();
        int age = Integer.parseInt(this.view.getTxtAge().getText());
        String password = this.view.getTxtPassword().getText();
        String gender = "";
        
        if(this.view.getRadBntMale().isSelected())
            gender = "Male";
        else if(this.view.getRadBntFemale().isSelected())
            gender = "Female";
        else
            gender = "Other";
        
        usuarios.add(new User(name, password, gender, age));
        
        LoginPanel loginPnl = new LoginPanel(usuarios);
        loginPnl.setVisible(true);
    }
}
