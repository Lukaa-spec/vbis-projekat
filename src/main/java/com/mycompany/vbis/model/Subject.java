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
public class Subject {
    private String id;
    private String name;
    private Lecturer lecturer;
    private ArrayList<SubjectSkill> skillTaught;

    public Subject() {
    }

    public Subject(String id, String name, Lecturer lecturer, ArrayList<SubjectSkill> skillTaught) {
        this.id = id;
        this.name = name;
        this.lecturer = lecturer;
        this.skillTaught = skillTaught;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public ArrayList<SubjectSkill> getSkillTaught() {
        return skillTaught;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public void setSkillTaught(ArrayList<SubjectSkill> skillTaught) {
        this.skillTaught = skillTaught;
    }
    
    
    
}
