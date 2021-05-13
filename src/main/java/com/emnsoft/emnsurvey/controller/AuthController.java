package com.emnsoft.emnsurvey.controller;

import javax.validation.Valid;

import com.emnsoft.emnsurvey.domain.dto.CreateUserDTO;
import com.emnsoft.emnsurvey.domain.dto.UserLoginDTO;
import com.emnsoft.emnsurvey.security.jwt.JwtTokenUtil;
import com.emnsoft.emnsurvey.service.JwtUserDetailsService;
import com.emnsoft.emnsurvey.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody UserLoginDTO dto) {
        try {
            this.authenticate(dto.getLogin(), dto.getPassword());
        }
        catch (DisabledException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthenticationErrorResponse("Account is disabled"));
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthenticationErrorResponse("The credentials provided aren't valid"));
        }
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(dto.getLogin());
        final String token = jwtTokenUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new JWTToken(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody CreateUserDTO user) {
        return userService.createuser(user);
    }

    private Authentication authenticate(String username, String password) throws DisabledException, BadCredentialsException {
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

    private static class JWTToken {

        private String idToken;
    
        JWTToken(String idToken) {
            this.idToken = idToken;
        }
    
        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }
    }

    private static class AuthenticationErrorResponse {
        private String message;

        AuthenticationErrorResponse(String message) {
            this.message = message;
        }

        @JsonProperty("message")
        String getMessage() {
            return message;
        }
    }
}
