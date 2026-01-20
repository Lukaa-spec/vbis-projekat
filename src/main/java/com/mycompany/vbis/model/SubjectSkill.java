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
public class SubjectSkill {
    private Skill skill;
    
    @JsonProperty("levelOfReadiness")
    private LevelOfReadiness readinessLevel;

    public SubjectSkill() {
    }

    public SubjectSkill(Skill skill, LevelOfReadiness readinessLevel) {
        this.skill = skill;
        this.readinessLevel = readinessLevel;
    }

    public Skill getSkill() {
        return skill;
    }

    public LevelOfReadiness getReadinessLevel() {
        return readinessLevel;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public void setReadinessLevel(LevelOfReadiness readinessLevel) {
        this.readinessLevel = readinessLevel;
    }
  
    
    
}
