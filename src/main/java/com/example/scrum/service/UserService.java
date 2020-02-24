package com.example.scrum.service;

import com.example.scrum.dto.UserRegisterDto;
import com.example.scrum.entity.Role;
import com.example.scrum.entity.User;
import com.example.scrum.entity.VerificationToken;
import com.example.scrum.mappers.UserMapper;
import com.example.scrum.repository.RoleRepository;
import com.example.scrum.repository.TokenRepository;
import com.example.scrum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final UserMapper userMapper;

    public void createVerificationToken(User user, String token){
        VerificationToken verificationToken = new VerificationToken(user, token);
        tokenRepository.save(verificationToken);
    }

    public VerificationToken getVerificationToken(String token){
        return tokenRepository.findByToken(token).orElseThrow(() -> new IllegalStateException("Given token does not exist!"));
    }

    public User addNewUser(UserRegisterDto userToAdd){

        if(Objects.isNull(userToAdd)){
            throw new NullPointerException("User object is null!");
        }

        userToAdd.setPassword(encodePassword(userToAdd.getPassword()));

        User user = userMapper.toEntity(userToAdd);
        user = addUserRole(user);

       return userRepository.save(user);
    }

    public User saveRegisteredUser(User user){
        return userRepository.save(user);
    }

    public String encodePassword(String password){

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.encode(password);
    }

    private User addUserRole(User user){

        Role userRole = roleRepository
                .findById(1L)
                .orElseThrow(() -> new IllegalStateException("ROLE_USER is not found in database! Database hasn't been initialized."));

        user.getRoles().add(userRole);

        return user;
    }

}
