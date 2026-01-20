/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vbis.dto;

import com.mycompany.vbis.model.JobRequirement;
import java.util.ArrayList;

/**
 *
 * @author Luka
 */
public class UpdateJobAdRequest {
    private String adId;
    private String title;
    private ArrayList<JobRequirement> requirements;

    public String getAdId() {
        return adId;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<JobRequirement> getRequirements() {
        return requirements;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRequirements(ArrayList<JobRequirement> requirements) {
        this.requirements = requirements;
    }
    
    
}
