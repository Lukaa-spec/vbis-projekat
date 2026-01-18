/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vbis.controller;

import com.mycompany.vbis.dto.UpdateProfileRequest;
import com.mycompany.vbis.jwt.JwtUtil;
import com.mycompany.vbis.model.JobAd;
import com.mycompany.vbis.model.Student;
import com.mycompany.vbis.model.User;
import com.mycompany.vbis.service.UserService;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mycompany.vbis.rdf.RdfJobAdService;

/**
 *
 * @author Luka
 */
@RestController
@RequestMapping("/users")
public class UserController {
       private final UserService userService;
       private final JwtUtil jwtUtil;

     public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

   // Registracija
      @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> payload) {
        User user = new User() {
            {
                setUsername(payload.get("username"));
                setPassword(payload.get("password"));
                setEmail(payload.get("email"));
            }
        };
        String type = payload.get("type");
        String agencyName = payload.get("agencyName");
        
        String result = userService.register(user, type, agencyName);
        
        // Provera da li je registracija uspešna
        if (result.equals("Uspešno registrovan!")) {
            return ResponseEntity.ok(Map.of("message", result));
        } else {
            return ResponseEntity.status(400).body(Map.of("error", result));
        }
    }

    // Login
 @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String password = payload.get("password");
        
        User user = userService.authenticate(username, password);
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Pogrešno korisničko ime ili lozinka!"));
        }
        
        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(Map.of("token", token));
    }


    //Update profile
@PutMapping("/profile")
public ResponseEntity<?> updateProfile(
        @RequestBody UpdateProfileRequest request
) {
    String loggedUsername =
        SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

    User updatedUser = userService.updateProfile(loggedUsername, request);

    if (updatedUser == null) {
        return ResponseEntity.status(404).body("Korisnik ne postoji!");
    }

    return ResponseEntity.ok(updatedUser);
}


//Dodavanje jobads
@PostMapping("/job-ads")
public ResponseEntity<?> addJobAd(
        @RequestHeader("Authorization") String authHeader,
        @RequestBody JobAd jobAd
) {
    if (!authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Nedostaje JWT token");
    }

    String token = authHeader.substring(7);
    String username = jwtUtil.extractUsername(token);

    try {
        JobAd created = userService.addJobAd(username, jobAd);
        return ResponseEntity.status(201).body(created);
    } catch (RuntimeException e) {
        return ResponseEntity.status(403).body(e.getMessage());
    }
}


//Pretraga svih oglasa
@GetMapping("/job-ads")
public ResponseEntity<?> getAllJobAds(
        @RequestHeader("Authorization") String authHeader
) {
    if (!authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Nedostaje JWT token");
    }

    String token = authHeader.substring(7);
    String username = jwtUtil.extractUsername(token);

    User user = userService.findByUsername(username);

    if (user == null) {
        return ResponseEntity.status(401).body("Nepostojeći korisnik");
    }

    if (!(user instanceof Student)) {
        return ResponseEntity.status(403)
                .body("Samo studenti mogu da pretražuju oglase");
    }

    return ResponseEntity.ok(userService.getAllJobAds());
}


//Traži posao
@PutMapping("/looking-for-job")
public ResponseEntity<?> setLookingForJob(
        @RequestHeader("Authorization") String authHeader,
        @RequestParam boolean value
) {
    if (!authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Nedostaje JWT token");
    }

    String token = authHeader.substring(7);
    String username = jwtUtil.extractUsername(token);

    try {
        Student student = userService.setLookingForJob(username, value);
        if (student == null) {
            return ResponseEntity.status(404).body("Korisnik ne postoji");
        }
        return ResponseEntity.ok(student);
    } catch (RuntimeException e) {
        return ResponseEntity.status(403).body(e.getMessage());
    }
}


//Studenti koji traže posao
@GetMapping("/students-looking-for-job")
public ResponseEntity<?> getStudentsLookingForJob(
        @RequestHeader("Authorization") String authHeader
) {
    if (!authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Nedostaje JWT token");
    }

    String token = authHeader.substring(7);
    String username = jwtUtil.extractUsername(token);

    try {
        ArrayList<Student> students = userService.findStudentsLookingForJob(username);
        return ResponseEntity.ok(students);
    } catch (RuntimeException e) {
        return ResponseEntity.status(403).body(e.getMessage());
    }
}


@GetMapping("/search-jobs")
public ResponseEntity<?> searchJobs(
        @RequestHeader("Authorization") String authHeader,
        @RequestParam String query
) {
    
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Nedostaje JWT token");
    }

    String token = authHeader.substring(7);
    String username = jwtUtil.extractUsername(token);

    try {
        ArrayList<JobAd> ads = userService.searchJobs(username, query);
        return ResponseEntity.ok(ads);
    }catch (RuntimeException e) {
    return ResponseEntity.status(403).body(e.getMessage());
    }
}







}
