/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDAO;
import dao.Connect;
import model.User;
import view.LoginPanel;
import view.MenuPanel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author ekpri
 */
public class ControlLoginPanel {
    private LoginPanel view;
    private ArrayList<User> usuarios;

    public ControlLoginPanel(LoginPanel view, ArrayList<User> usuarios) {
        this.view = view;
        this.usuarios = usuarios;
    }

    public ControlLoginPanel(LoginPanel view) {
        this.view = view;
    }
    
    public void loginUser(){
        User user = new User(view.getTxtUsername().getText(), 
                             view.getTxtPassword().getText());
        Connect connect = new Connect();
        try{
            Connection conn = connect.getConnection();
            UserDAO dao = new UserDAO(conn);
            ResultSet res = dao.check(user);
            
            if(res.next()){
                JOptionPane.showMessageDialog(view, "Login Feito", "Aviso", 
                                              JOptionPane.INFORMATION_MESSAGE);
                String name = res.getString("name");
                String password = res.getString("password");
                String gender = res.getString("gender");
                int age = res.getInt("age");
                MenuPanel menu = new MenuPanel(new User(name,password,gender, age));
                menu.setVisible(true);
                view.setVisible(false);
            }else{
                JOptionPane.showMessageDialog(view, "Login não efetuado", "Erro"
                                              , JOptionPane.ERROR_MESSAGE);
            }
            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(view, e, "Erro", 
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
}
