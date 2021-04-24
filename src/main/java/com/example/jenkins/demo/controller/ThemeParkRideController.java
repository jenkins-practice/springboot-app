package com.example.jenkins.demo.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ThemeParkRideController {

    @GetMapping(value = "/ride/{id}")
    public ResponseEntity<String> getRide(@PathVariable long id){
        System.out.println("hello world");
        return ResponseEntity.ok().body("hello rider");
    }

}