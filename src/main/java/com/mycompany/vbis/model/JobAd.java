/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vbis.model;

/**
 *
 * @author Luka
 */
public class JobAd {
    private String id;
    private Skill skill;
    private Priority priority;
    private LevelOfReadiness levelOfReadiness;

    public JobAd() {
    }

    public JobAd(String id, Skill skill, Priority priority, LevelOfReadiness levelOfReadiness) {
        this.id = id;
        this.skill = skill;
        this.priority = priority;
        this.levelOfReadiness = levelOfReadiness;
    }

    public String getId() {
        return id;
    }

    public Skill getSkill() {
        return skill;
    }

    public Priority getPriority() {
        return priority;
    }

    public LevelOfReadiness getLevelOfReadiness() {
        return levelOfReadiness;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setLevelOfReadiness(LevelOfReadiness levelOfReadiness) {
        this.levelOfReadiness = levelOfReadiness;
    }
    
    
    
    
}
