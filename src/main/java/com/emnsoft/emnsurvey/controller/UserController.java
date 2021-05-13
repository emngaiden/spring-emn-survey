package com.emnsoft.emnsurvey.controller;

import com.emnsoft.emnsurvey.domain.User;
import com.emnsoft.emnsurvey.repository.UserRepository;
import com.emnsoft.emnsurvey.service.UserService;
import com.emnsoft.emnsurvey.utils.ResponseEntityUtils;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return ResponseEntityUtils.wrapOrNotFound(userRepository.findById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/users/delete")
    public ResponseEntity<Void> deleteUser(@RequestParam String id){
        User user = getUser(id).getBody();
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if(user.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return userService.updateUser(user);
    }
}
