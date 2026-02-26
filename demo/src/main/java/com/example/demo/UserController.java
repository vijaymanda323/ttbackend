package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@CrossOrigin(origins = "*")
public class UserController {
 @Autowired
 UserRepository userRepo;
 @GetMapping("/demo")   
    public String demo() {
        return "Hello from UserController!";
    }
@GetMapping("/users")

public List<Users>getAllUsers(){
    return this.userRepo.findAll();
}
@PostMapping("/register")
public ResponseEntity<String> register(@RequestBody Users user) {
    // use derived query method that matches entity property
    Users u = this.userRepo.findByEmail(user.getEmail());
    if (u != null) {
        return ResponseEntity.badRequest().body("Email already exists");
    }
    this.userRepo.save(user);
    return ResponseEntity.ok("User registered successfully");
}
}