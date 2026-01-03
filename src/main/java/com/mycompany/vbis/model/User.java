/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vbis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Luka
 */
public abstract class User {
    
     
    @JsonProperty("_key")
    protected String username;
    
    protected String password;
    protected String email;

    
    public User() {}
    public User (String username, String password, String email) {
   
        this.username = username;
        this.password = password;
        this.email = email;
    }



    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
