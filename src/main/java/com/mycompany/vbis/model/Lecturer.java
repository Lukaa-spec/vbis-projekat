/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vbis.model;

import java.util.ArrayList;

/**
 *
 * @author Luka
 */
public class Lecturer {
    private String id;
    private String fullName;
    private ArrayList<Skill> expertInSkills;

    public Lecturer() {
    }

    public Lecturer(String id, String fullName, ArrayList<Skill> expertInSkills) {
        this.id = id;
        this.fullName = fullName;
        this.expertInSkills = expertInSkills;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public ArrayList<Skill> getExpertInSkills() {
        return expertInSkills;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setExpertInSkills(ArrayList<Skill> expertInSkills) {
        this.expertInSkills = expertInSkills;
    }
    
    
    
}
