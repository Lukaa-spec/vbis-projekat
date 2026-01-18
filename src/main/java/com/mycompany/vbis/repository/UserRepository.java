/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vbis.repository;


import com.arangodb.ArangoDatabase;
import com.arangodb.ArangoCursor;
import com.arangodb.model.AqlQueryOptions;
import com.mycompany.vbis.model.User;
import com.mycompany.vbis.model.Student;
import com.mycompany.vbis.model.Agency;
import com.mycompany.vbis.model.JobAd;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
/**
 *
 * @author Luka
 */
@Repository
public class UserRepository {
private ArangoDatabase db;

   public UserRepository(ArangoDatabase db) {
    this.db = db;
}

 //Save user
public void save(User user) {
    if (user instanceof Student) {
        db.collection("students").insertDocument(user);
    } else if (user instanceof Agency) {
        db.collection("agencies").insertDocument(user);
    }
}
    
    
 // Find by username
public User findByUsername(String username) {
    // Pretraga u students kolekciji
    String aql = "FOR u IN students FILTER u._key == @username RETURN u";
    Map<String, Object> bindVars = Map.of("username", username);
    ArangoCursor<Student> studentCursor = db.query(
        aql, 
        Student.class,
        bindVars
    );

    if (studentCursor.hasNext()) {
        return studentCursor.next();
    }

    // Pretraga u agencies kolekciji
    aql = "FOR u IN agencies FILTER u._key == @username RETURN u";
    ArangoCursor<Agency> agencyCursor = db.query(
        aql, 
        Agency.class,
        bindVars
    );

    if (agencyCursor.hasNext()) {
        return agencyCursor.next();
    }

       return null;
}

//Email već postoji
public boolean emailExists(String email) {
    String aqlStudents = "FOR u IN students FILTER u.email == @email RETURN 1";
    Map<String, Object> vars = Map.of("email", email);
    ArangoCursor<Integer> cursorStudents = db.query(aqlStudents, Integer.class, vars);
    
    if (cursorStudents.hasNext()) return true;

   
    String aqlAgencies = "FOR u IN agencies FILTER u.email == @email RETURN 1";
    ArangoCursor<Integer> cursorAgencies = db.query(aqlAgencies, Integer.class, vars);
    
    return cursorAgencies.hasNext();
}


    //Update user
    public boolean updateUser(User user) {
        String collection =
        (user instanceof Student) ? "students" : "agencies";

    db.collection(collection).updateDocument(
            user.getUsername(), //username mi je key
            user
    );
        return true;
}

  
//Izlistaj sve jobads
public ArrayList<JobAd> findAllJobAds() {

    String aql = """
        FOR a IN agencies
            FOR job IN a.jobAds
                RETURN job
    """;

    ArangoCursor<JobAd> cursor = db.query(aql, JobAd.class);

    ArrayList<JobAd> result = new ArrayList<>();
    cursor.forEachRemaining(result::add);

    return result;
}



public ArrayList<Student> findStudentsLookingForJob() {

    String query = """
        FOR s IN students
            FILTER s.lookingForJob == true
            RETURN s
    """;

    ArangoCursor<Student> cursor = db.query(query, Student.class);

    ArrayList<Student> result = new ArrayList<>();
    cursor.forEachRemaining(result::add);

    return result;
}


public ArrayList<JobAd> searchJobAds(String query) {
    String aql = """
        FOR a IN agencies
          FOR j IN a.jobAds
            FILTER LIKE(j.title, @query, true)
            OR LENGTH(
              FOR r IN j.requirements
                FILTER LIKE(r.skill.name, @query, true)
                RETURN 1
            ) > 0
            RETURN j
        """;
    
    Map<String, Object> bindVars = new HashMap<>();
    bindVars.put("query", "%" + query + "%");
    
    ArangoCursor<JobAd> cursor = db.query(
        aql, 
        JobAd.class,
        bindVars,
        new AqlQueryOptions()
    );
    
    return new ArrayList<>(cursor.asListRemaining()); // Vraća listu pronađenih oglasa
}
    
    
    
}
