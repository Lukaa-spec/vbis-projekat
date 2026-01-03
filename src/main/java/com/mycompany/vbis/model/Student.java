/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vbis.model;


import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Luka
 */
public class Student extends User {
    private ArrayList<String> skills;
    private Map<String, Integer> passedExams;
    private boolean lookingForJob;

    public Student() {
    }

    public Student(ArrayList<String> skills, Map<String, Integer> passedExams, boolean lookingForJob) {
        this.skills = skills;
        this.passedExams = passedExams;
        this.lookingForJob = lookingForJob;
    }

    public Student(ArrayList<String> skills, Map<String, Integer> passedExams, boolean lookingForJob, String id, String username, String password, String email) {
        super(username, password, email);
        this.skills = skills;
        this.passedExams = passedExams;
        this.lookingForJob = lookingForJob;
    }

    public Student(String username, String password, String email) {
        super( username, password, email);
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public Map<String, Integer> getPassedExams() {
        return passedExams;
    }

    public boolean isLookingForJob() {
        return lookingForJob;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public void setPassedExams(Map<String, Integer> passedExams) {
        this.passedExams = passedExams;
    }

    public void setLookingForJob(boolean lookingForJob) {
        this.lookingForJob = lookingForJob;
    }

   
    
    
    
    
}
