package com.emnsoft.emnsurvey.controller;


import org.bson.BasicBSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommunicationController {
    

    @PostMapping("/communication")
    public ResponseEntity<Void> comunicate(@RequestBody String comm) {
        System.out.println(comm);
        return ResponseEntity.ok().build();
    }

}
