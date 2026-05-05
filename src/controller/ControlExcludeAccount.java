/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDAO;
import dao.Connect;
import model.User;
import view.ExcludeAccountPanel;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ekpri
 */
public class ControlExcludeAccount {
    private ExcludeAccountPanel view;
    private User user;
    
    public ControlExcludeAccount(ExcludeAccountPanel view, User user){
        this.view = view;
        this.user = user;
    }
    
    public void remove(){
        int option = JOptionPane.showConfirmDialog(view, 
                                         "Deseja realmente excluir o cadastro?", 
                                         "Aviso", JOptionPane.YES_NO_OPTION);
        if(option != 1){
            Connect connect = new Connect();
            try{
                Connection conn = connect.getConnection();
                UserDAO dao= new UserDAO(conn);
                dao.remover(user);
                JOptionPane.showMessageDialog(view, 
                                      "Usuario removido com Sucesso!", 
                                      "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(view, e.getMessage(), "Erro",
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
