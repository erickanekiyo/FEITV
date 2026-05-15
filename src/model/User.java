/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ekpri
 */
public class User {
    private String name, password, gender;
    private int age, id;
    private ListReproduction listReproduction;

    public User() {
        this.listReproduction = new ListReproduction();
    }
    
    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.listReproduction = new ListReproduction();
    }

    public User(String name, String password, String gender, int age) {
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.listReproduction = new ListReproduction();
    }

    public User(String name, String password, String gender, int age, int id) {
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.id = id;
        this.listReproduction = new ListReproduction();
    }

    //GETTERS & SETTERS
    public ListReproduction getListReproduction() {
        return listReproduction;
    }
    public void setListReproduction(ListReproduction listReproduction) {
        this.listReproduction = listReproduction;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
