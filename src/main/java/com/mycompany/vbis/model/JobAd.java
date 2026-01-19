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
public class JobAd {
    private String id;
    private String title;
    private String agencyName;

    private ArrayList<JobRequirement> requirements;

    public JobAd() {
    }

    public JobAd(String id, String title, String agencyName, ArrayList<JobRequirement> requirements) {
        this.id = id;
        this.title = title;
        this.agencyName = agencyName;
        this.requirements = requirements;
    }

    public String getAgencyName() {
        return agencyName;
    }
    
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<JobRequirement> getRequirements() {
        return requirements;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRequirements(ArrayList<JobRequirement> requirements) {
        this.requirements = requirements;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }
    
    
    
    

    
    
    
    
    
}
