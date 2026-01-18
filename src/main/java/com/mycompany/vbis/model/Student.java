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
   
    private ArrayList<ExamAttempt> examHistory;
    private ArrayList<String> skills;
    private boolean lookingForJob;

    public Student() {
        this.skills = new ArrayList<>();
        this.examHistory = new ArrayList<>();
        this.lookingForJob = false;
    }


    public Student(boolean lookingForJob, String username, String password, String email) {
        super(username, password, email);
        this.skills = new ArrayList<>();
        this.examHistory =  new ArrayList<>();
        this.lookingForJob = lookingForJob;
    }
    

    public Student(String username, String password, String email) {
        super( username, password, email);
        this.skills = new ArrayList<>();
        this.examHistory = new ArrayList<>();
        this.lookingForJob = false;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public boolean isLookingForJob() {
        return lookingForJob;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public void setLookingForJob(boolean lookingForJob) {
        this.lookingForJob = lookingForJob;
    }

    public ArrayList<ExamAttempt> getExamHistory() {
        return examHistory;
    }

    public void setExamHistory(ArrayList<ExamAttempt> examHistory) {
        this.examHistory = examHistory;
    }

   
    
    
    
    
}
