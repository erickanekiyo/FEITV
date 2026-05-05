/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.Connect;
import dao.UserDAO;
import view.SignInPanel;
import view.LoginPanel;
import model.User;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ekpri
 */
public class ControlSignInPanel {
    private SignInPanel view;
    private LoginPanel login;
    private ArrayList<User> usuarios = new ArrayList<>();

    public ControlSignInPanel(SignInPanel view) {
        this.view = view;
    }
    
//    public void register(){
//        String name = this.view.getTxtUsername().getText();
//        int age = Integer.parseInt(this.view.getTxtAge().getText());
//        String password = this.view.getTxtPassword().getText();
//        String gender = "";
//        
//        if(this.view.getRadBntMale().isSelected())
//            gender = "Male";
//        else if(this.view.getRadBntFemale().isSelected())
//            gender = "Female";
//        else
//            gender = "Other";
//        
//        usuarios.add(new User(name, password, gender, age));
//        
//        LoginPanel loginPnl = new LoginPanel(usuarios);
//        loginPnl.setVisible(true);
//    }
    
    public void register() {
        String name = view.getTxtUsername().getText();
        String password = view.getTxtPassword().getText();
        String gender = "";
        if(this.view.getRadBntMale().isSelected())
            gender = "Male";
        else if(this.view.getRadBntFemale().isSelected())
            gender = "Female";
        else
            gender = "Other";
        int age = Integer.parseInt(view.getTxtAge().getText());

        User user = new User(name, password, gender, age);
        Connect connect = new Connect();

        try (Connection conn = connect.getConnection()) {
            UserDAO dao = new UserDAO(conn);
            dao.inserir(user);

            JOptionPane.showMessageDialog(login, "Usuário Cadastrado!", "Aviso", 
                                          JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(login, "Usuário não cadastrado: " 
                                          + e.getMessage(), "Erro", 
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
}
