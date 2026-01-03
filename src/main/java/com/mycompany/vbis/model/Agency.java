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
public class Agency extends User {
    private String agencyName;
    private ArrayList<JobAd> jobAds;

    public Agency() {
    }

    public Agency(String agencyName, ArrayList<JobAd> jobAds) {
        this.agencyName = agencyName;
        this.jobAds = jobAds;
    }

    public Agency(String username, String password, String email, String agencyName) {
        super(username, password, email);
        this.agencyName = agencyName;
    }
    
    public String getAgencyName() {
        return agencyName;
    }

    public ArrayList<JobAd> getJobAds() {
        return jobAds;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public void setJobAds(ArrayList<JobAd> jobAds) {
        this.jobAds = jobAds;
    }


    
    
    
}
