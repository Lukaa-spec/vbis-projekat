/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vbis.rdf;

import com.mycompany.vbis.model.JobAd;
import com.mycompany.vbis.model.JobRequirement;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb2.TDB2Factory;
import org.apache.jena.vocabulary.RDF;
import org.springframework.stereotype.Service;

/**
 *
 * @author Luka
 */
@Service
public class RdfJobAdService {

    private static final String NS = "http://vbis.com/schema#";
    private static final String BASE = "http://vbis.com/resource/";

    private final Dataset dataset;

    public RdfJobAdService() {
        this.dataset = TDB2Factory.connectDataset("tdb");
    }

 public void saveJobAd(String agencyUsername, JobAd jobAd) {
    dataset.begin(ReadWrite.WRITE);
    try {
        Model model = dataset.getDefaultModel();

         
        Property publishedBy = model.createProperty(NS, "publishedBy");
        Property titleProp = model.createProperty(NS, "title");
        Property hasRequirement = model.createProperty(NS, "hasRequirement");
        Property skillProp = model.createProperty(NS, "skill");
        Property priorityProp = model.createProperty(NS, "priority");
        Property readinessProp = model.createProperty(NS, "levelOfReadiness");

        //Kreiranje resursa agencije
        Resource agencyRes = model.createResource(BASE + "agency/" + agencyUsername)
                .addProperty(RDF.type, model.createResource(NS + "EmploymentAgency"));

        //Kreiranj oglasa i povezivanje sa agencijom
        Resource jobRes = model.createResource(BASE + "job/" + jobAd.getId())
                .addProperty(RDF.type, model.createResource(NS + "JobAd"))
                .addProperty(titleProp, jobAd.getTitle());
        
        //Agencija -> Oglas
        agencyRes.addProperty(publishedBy, jobRes);

        
        for (JobRequirement req : jobAd.getRequirements()) {
            Resource skillRes = model.createResource(BASE + "skill/" + req.getSkill().getName().replace(" ", "_"))
                    .addProperty(RDF.type, model.createResource(NS + "Skill"));

            Resource reqNode = model.createResource()
                    .addProperty(skillProp, skillRes)
                    .addLiteral(priorityProp, req.getPriority().name())
                    .addLiteral(readinessProp, req.getLevelOfReadiness().name());

            jobRes.addProperty(hasRequirement, reqNode);
        }

        dataset.commit();
    } finally {
        dataset.end();
    }
}
 
 public void printAllData() {
    dataset.begin(ReadWrite.READ);
    try {
        Model model = dataset.getDefaultModel();
        model.write(System.out, "TURTLE");
    } finally {
        dataset.end();
    }
}

}
