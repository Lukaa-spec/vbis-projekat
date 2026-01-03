/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vbis.repository;


import com.arangodb.ArangoDatabase;
import com.arangodb.ArangoCursor;
import com.mycompany.vbis.model.User;
import com.mycompany.vbis.model.Student;
import com.mycompany.vbis.model.Agency;
import java.util.Collections;
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


    //update user
    public boolean updateUser(User user) {
        String collection =
        (user instanceof Student) ? "students" : "agencies";

    db.collection(collection).updateDocument(
            user.getUsername(),
            user
    );
        return true;
}

    
    
    
}
