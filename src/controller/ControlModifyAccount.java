/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDAO;
import dao.Connect;
import model.User;
import view.ModifyAccountPanel;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;


/**
 *
 * @author ekpri
 */
public class ControlModifyAccount {
    private ModifyAccountPanel view;
    private User user;
    private Connection conn;
    
    public ControlModifyAccount(ModifyAccountPanel view, User user, 
            Connection conn){
        this.view = view;
        this.user = user;
        this.conn = conn;
    }
    
    public void update(){
        String newName = view.getTxtNewName().getText().trim();
        String newPassword = view.getTxtNewPassword().getText().trim();
        
        if (newName.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Preencha todos os campos!");
            return;
        }

        try {
            UserDAO dao = new UserDAO(conn);
            user.setName(newName);
            user.setPassword(newPassword);
            
            dao.update(user);

            JOptionPane.showMessageDialog(view, "Dados atualizados com sucesso!");
            view.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Erro ao atualizar: " + e.getMessage());
        }
    }
}
