/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vbis.model;

/**
 *
 * @author Luka
 */
public class ExamAttempt {
    private String id;
    private Subject subject;
    private int grade;

    public ExamAttempt() {
    }

    public ExamAttempt(String id, Subject subject, int grade) {
        this.id = id;
        this.subject = subject;
        this.grade = grade;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }


    public String getId() {
        return id;
    }


    public int getGrade() {
        return grade;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
    
    
   
}
