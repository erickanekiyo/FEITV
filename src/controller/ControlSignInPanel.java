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
    private User user;

    public ControlSignInPanel(SignInPanel view) {
        this.view = view;
    }
    
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
        
        

        User newUser = new User(name, password, gender, age);
        Connect connect = new Connect();

        try (Connection conn = connect.getConnection()) {
            UserDAO dao = new UserDAO(conn);
            dao.insert(newUser);

            JOptionPane.showMessageDialog(login, "Usuário Cadastrado!", "Aviso", 
                                          JOptionPane.INFORMATION_MESSAGE);
            view.setVisible(false);
            LoginPanel loginPnl = new LoginPanel();
            loginPnl.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(login, "Usuário não cadastrado: " 
                                          + e.getMessage(), "Erro", 
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
}
