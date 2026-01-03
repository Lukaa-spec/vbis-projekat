/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vbis.config;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Luka
 */
@Configuration
public class ArangoConfig {

    @Bean
  public ArangoDatabase arangoDatabase() {
        ArangoDB arangoDB = new ArangoDB.Builder()
                .host("127.0.0.1", 8529)  
                .user("root")                  
                .build();

        // Kreiraj/izaberi bazu
        String dbName = "vbisdb";
        if (!arangoDB.getDatabases().contains(dbName)) {
            arangoDB.createDatabase(dbName);
        }

        return arangoDB.db(dbName);
    }
    
    
}
