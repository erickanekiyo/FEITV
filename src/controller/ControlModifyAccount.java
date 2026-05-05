/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDAO;
import dao.Connect;
import model.User;
import view.ModifyAccountPanel;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author ekpri
 */
public class ControlModifyAccount {
    private ModifyAccountPanel view;
    private User user;
    
    public ControlModifyAccount(ModifyAccountPanel view, User user){
        this.view = view;
        this.user = user;
    }
    
    public void update(){
        String newName = user.getName();
        String newPassword = view.getTxtNewPassword().getText();
        User user = new User(newName, newPassword);
        Connect connect = new Connect();
        
        try{
            Connection conn = connect.getConnection();
            UserDAO dao = new UserDAO(conn);
            dao.atualizar(user);
            JOptionPane.showMessageDialog(view, "Alteração Realizada", "Aviso", 
                                          JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(view, e, "Erro", 
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
