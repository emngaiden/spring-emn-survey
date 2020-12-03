package com.emnsoft.emnsurvey.controller;

import javax.validation.Valid;

import com.emnsoft.emnsurvey.domain.dto.UserLoginDTO;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody UserLoginDTO dto) {
        throw new NotImplementedException();
    }
}
