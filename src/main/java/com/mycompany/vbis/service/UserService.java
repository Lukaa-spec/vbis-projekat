/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vbis.service;

import com.mycompany.vbis.dto.UpdateProfileRequest;
import com.mycompany.vbis.model.Agency;
import com.mycompany.vbis.model.Student;
import com.mycompany.vbis.model.User;
import com.mycompany.vbis.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author Luka
 */
@Service
public class UserService {
    private UserRepository repository;
    
     public UserService(UserRepository repository) {
        this.repository = repository;
    }
     
       // Registracija korisnika
  public String register(User user, String type, String agencyName) {

    if (user.getPassword() == null || user.getPassword().length() < 8) {
        return "Lozinka mora imati najmanje 8 karaktera!";
    }

    if (user.getEmail() == null || !user.getEmail().contains("@")) {
        return "Email mora biti validan!";
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

        // Email
        if (request.getEmail() != null && request.getEmail().contains("@")) {
            user.setEmail(request.getEmail());
        }

        // Password
        if (request.getPassword() != null && request.getPassword().length() >= 8) {
            user.setPassword(request.getPassword());
        }

        // Agency name
        if (user instanceof Agency agency) {
            if (request.getAgencyName() != null && !request.getAgencyName().isBlank()) {
                agency.setAgencyName(request.getAgencyName());
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


    
}
