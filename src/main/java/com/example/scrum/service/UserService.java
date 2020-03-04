package com.example.scrum.service;

import com.example.scrum.dto.UserRegisterDto;
import com.example.scrum.entity.Role;
import com.example.scrum.entity.User;
import com.example.scrum.entity.VerificationToken;
import com.example.scrum.exceptions.UserNotFoundException;
import com.example.scrum.mappers.UserMapper;
import com.example.scrum.repository.RoleRepository;
import com.example.scrum.repository.TokenRepository;
import com.example.scrum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final UserMapper userMapper;

    public User changeUserPassword(Long userId, String newPassword){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User object is null!"));

        user.setPassword(encodePassword(newPassword));

        return userRepository.save(user);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email){
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with given email does not exist!"));
    }

    public User findById(Long id){
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with given id does not exist!"));
    }

    public String createVerificationToken(User user){

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(user, token);
        tokenRepository.save(verificationToken);

        return token;
    }

    public VerificationToken getVerificationToken(String token){
        return tokenRepository.findByToken(token).orElseThrow(() -> new IllegalStateException("Given token does not exist!"));
    }

    public User addNewUser(UserRegisterDto userToAdd){

        if(Objects.isNull(userToAdd)){
            throw new UserNotFoundException("User object is null!");
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
