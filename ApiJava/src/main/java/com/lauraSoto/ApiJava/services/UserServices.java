package com.lauraSoto.ApiJava.services;

import java.sql.Date;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lauraSoto.ApiJava.models.User;
import com.lauraSoto.ApiJava.repositories.UserRepository;
import com.lauraSoto.ApiJava.utiles.JwtTokenUtil;

@Service
public class UserServices {
    private final UserRepository userRepository;

    private final JwtTokenUtil jwtTokenUtil;

    public UserServices(UserRepository userRepository, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s not found", userId)));
    }

    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        user.setLastLogin(new java.util.Date());
        user.setToken(this.jwtTokenUtil.generateToken(user.getEmail()));
        return userRepository.saveAndFlush(user);
    }

    public Boolean findByEmail(String email) {
        Optional<User> result = userRepository.findByEmail(email);
        if (result.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean loginUser(String username, String password) {
        // primero encontrar el usuario por su email
        Optional<User> result = userRepository.findByName(username);
        // si no lo podemos encontrar por su email, retornamos false
        if (result == null) {
            return false;
        } else {
            // si el password coincide devolvemos true, sino, devolvemos false
            if (BCrypt.checkpw(password, result.get().getPassword())) {
                result.get().setLastLogin(new java.util.Date());
                return true;
            } else {
                return false;
            }
        }

    }
}
