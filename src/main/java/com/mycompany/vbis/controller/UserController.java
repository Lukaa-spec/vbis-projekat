/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vbis.controller;

import com.mycompany.vbis.dto.UpdateProfileRequest;
import com.mycompany.vbis.jwt.JwtUtil;
import com.mycompany.vbis.model.User;
import com.mycompany.vbis.service.UserService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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



}
