/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vbis.service;

import com.mycompany.vbis.dto.UpdateProfileRequest;
import com.mycompany.vbis.model.Agency;
import com.mycompany.vbis.model.JobAd;
import com.mycompany.vbis.model.Student;
import com.mycompany.vbis.model.User;
import com.mycompany.vbis.rdf.RdfJobAdService;
import com.mycompany.vbis.repository.UserRepository;
import java.util.ArrayList;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 *
 * @author Luka
 */
@Service
public class UserService {
    private UserRepository repository;
    
     private final RdfJobAdService rdfJobAdService;

public UserService(UserRepository repository,
                   RdfJobAdService rdfJobAdService) {
    this.repository = repository;
    this.rdfJobAdService = rdfJobAdService;
}

     
       // Registracija korisnika
  public String register(User user, String type, String agencyName) {

    if (user.getPassword() == null || user.getPassword().length() < 8) {
        return "Lozinka mora imati najmanje 8 karaktera!";
    }

    if (user.getEmail() == null || !user.getEmail().contains("@")) {
        return "Email mora biti validan!";
    }
    
    if (repository.emailExists(user.getEmail())) {
        return "Email već postoji!";
    }

    if (repository.findByUsername(user.getUsername()) != null) {
        return "Korisničko ime već postoji!";
    }

    if ("STUDENT".equalsIgnoreCase(type)) {
        Student student = new Student(
            user.getUsername(),
            user.getPassword(),
            user.getEmail()
        );
        repository.save(student);

    } else if ("AGENCY".equalsIgnoreCase(type)) {

        if (agencyName == null || agencyName.isBlank()) {
            return "Ime agencije je obavezno!";
        }

        Agency agency = new Agency(
            user.getUsername(),
            user.getPassword(),
            user.getEmail(),
            agencyName
        );
        repository.save(agency);

    } else {
        return "Nepoznat tip korisnika!";
    }

    return "Uspešno registrovan!";
}


    
        //Login
    public String login(String username, String password) {
        User user = repository.findByUsername(username);
        if (user == null) return "Korisnik ne postoji!";
        if (!user.getPassword().equals(password)) return "Pogrešna lozinka!";
        return "Uspešno prijavljen!";
    }
    
    
   
// Update profile
public User updateProfile(String loggedUsername, UpdateProfileRequest request) {
    User user = repository.findByUsername(loggedUsername);

    if (user == null) {
        return null;
    }

   if (request.getEmail() != null && request.getEmail().contains("@")) {
        String newEmail = request.getEmail().trim();
        
        if (!newEmail.equalsIgnoreCase(user.getEmail())) {
            if (repository.emailExists(newEmail)) {
                throw new RuntimeException("Email već postoji!");
            }
            user.setEmail(newEmail);
        }
    }
    if (request.getPassword() != null && request.getPassword().length() >= 8) {
        user.setPassword(request.getPassword());
    }
    
    if (user instanceof Student student) {
        if (request.getLookingForJob() != null) {
            student.setLookingForJob(request.getLookingForJob());
        }
    }

    //Ažuriranje agencyName u agenciji i u oglasu
    if (user instanceof Agency agency) {
        String newName = request.getAgencyName();
        if (newName != null && !newName.isBlank()) {
        
            agency.setAgencyName(newName);

            if (agency.getJobAds() != null) {
                for (JobAd ad : agency.getJobAds()) {
                    ad.setAgencyName(newName);
                }
            }
        }
    }

    repository.updateUser(user);
    return user;
}
    
    
    public User authenticate(String username, String password) {
    User user = repository.findByUsername(username.trim());
    if (user == null) return null;
    String storedPassword = user.getPassword() == null ? "" : user.getPassword().trim();
    String inputPassword = password == null ? "" : password.trim();
    if (!storedPassword.equals(inputPassword)) return null;
    return user;
}

    
    //Dodaj oglas
    public JobAd addJobAd(String loggedUsername, JobAd jobAd) {
         User user = repository.findByUsername(loggedUsername);

    if (user == null) return null;

    if (!(user instanceof Agency agency)) {
        throw new RuntimeException("Samo agencija može dodati oglas");
    }

    jobAd.setId(UUID.randomUUID().toString());
    
    jobAd.setAgencyName(agency.getAgencyName());

    agency.addJobAd(jobAd);

    repository.updateUser(agency);
    
    rdfJobAdService.saveJobAd(loggedUsername, jobAd);

    return jobAd;
    }
    
    public ArrayList<JobAd> getAllJobAds() {
    return repository.findAllJobAds();
}

    public User findByUsername(String username) {
    return repository.findByUsername(username);
}
    
    
    public Student setLookingForJob(String username, boolean value) {
    User user = repository.findByUsername(username);

    if (user == null) return null;

    if (!(user instanceof Student student)) {
        throw new RuntimeException("Samo student može menjati ovaj status");
    }

    student.setLookingForJob(value);

    repository.updateUser(student);

    return student;
}

    //Pronadji studente koji traže posao
    public ArrayList<Student> findStudentsLookingForJob(String agencyUsername) {

    User user = repository.findByUsername(agencyUsername);

    if (user == null) {
        throw new RuntimeException("Korisnik ne postoji");
    }

    if (!(user instanceof Agency)) {
        throw new RuntimeException("Samo agencije mogu da pretražuju studente koji traže posao");
    }

    return repository.findStudentsLookingForJob();
}
    

    public ArrayList<JobAd> searchJobs(String username, String query) {
   
    User user = repository.findByUsername(username);
    if (user == null) {
        throw new RuntimeException("Korisnik nije pronađen");
    }

    if (query == null || query.trim().isEmpty()) {
        return new ArrayList<>();
    }

    
    return repository.searchJobAds(query);
}
    
    



    
}
