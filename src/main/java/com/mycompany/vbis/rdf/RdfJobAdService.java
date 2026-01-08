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

    public void saveJobAd(JobAd jobAd) {

        dataset.begin(ReadWrite.WRITE);
        try {
            Model model = dataset.getDefaultModel();

            Property titleProp = model.createProperty(NS, "title");
            Property requiresSkill = model.createProperty(NS, "requiresSkill");
            Property priorityProp = model.createProperty(NS, "priority");
            Property readinessProp = model.createProperty(NS, "levelOfReadiness");

            Resource jobRes = model.createResource(BASE + "job/" + jobAd.getId())
                    .addProperty(RDF.type, model.createResource(NS + "JobAd"))
                    .addProperty(titleProp, jobAd.getTitle());

            for (JobRequirement req : jobAd.getRequirements()) {

                Resource skillRes = model.createResource(
                        BASE + "skill/" + req.getSkill().getName()
                ).addProperty(RDF.type, model.createResource(NS + "Skill"));

                jobRes.addProperty(requiresSkill, skillRes)
                      .addLiteral(priorityProp, req.getPriority().name())
                      .addLiteral(readinessProp, req.getLevelOfReadiness().name());
            }

            dataset.commit();
        } finally {
            dataset.end();
        }
    }
}
