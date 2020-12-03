package com.emnsoft.emnsurvey.service;

import com.emnsoft.emnsurvey.repository.UserRepository;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.emnsoft.emnsurvey.domain.User;
import com.emnsoft.emnsurvey.domain.dto.CreateUserDTO;

@Service
public class UserService {
    
    private final PasswordEncoder encoder;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder encoder){
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public ResponseEntity<User> updateUser(User user) {
        if(user.getPassword() == null || user.getPassword().isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        final Optional<User> og = userRepository.findById(user.getId());
        if(!og.isPresent()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        User found = og.get();
        user.setActive(found.isActive());
        user.setPassword(found.getPassword());
        found = userRepository.save(found);
        return ResponseEntity.ok().body(found);
    }

    public ResponseEntity<User> createuser(CreateUserDTO dto) {
        User user = new User();
        user.setActive(false);
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLangKey(dto.getLangKey());
        user.setLogin(dto.getLogin());
        user.setLastName(dto.getLastName());
        final ObjectId id = ObjectId.get();
        user.setId(id.toString());
        user.setPassword(encoder.encode(dto.getPassword()));
        final User created = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
