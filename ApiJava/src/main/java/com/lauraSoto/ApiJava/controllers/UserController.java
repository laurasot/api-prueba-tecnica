package com.lauraSoto.ApiJava.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lauraSoto.ApiJava.mappers.UserDto;
import com.lauraSoto.ApiJava.models.User;
import com.lauraSoto.ApiJava.services.UserServices;

@RestController
@RequestMapping("/users")
public class UserController {

    @Value("${password.regex}")
    private String passwordRegex;

    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody User newUser) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(newUser.getEmail());
        if (!m.matches()) {
            return new ResponseEntity<Object>(
                    new com.lauraSoto.ApiJava.exception.Error(
                            String.format("user %s not create, invalid email", newUser.getEmail())),
                    HttpStatus.BAD_REQUEST);
        }

        String pPattern = passwordRegex;
        java.util.regex.Pattern e = java.util.regex.Pattern.compile(pPattern);
        java.util.regex.Matcher r = e.matcher(newUser.getPassword());
        if (!r.matches()) {
            return new ResponseEntity<Object>(
                    new com.lauraSoto.ApiJava.exception.Error(
                            String.format("user %s not create, invalid password", newUser.getName())),
                    HttpStatus.BAD_REQUEST);
        }

        if (userServices.findByEmail(newUser.getEmail())) {
            return new ResponseEntity<Object>(
                    new com.lauraSoto.ApiJava.exception.Error(
                            String.format("user %s not create, email already registered", newUser.getName())),
                    HttpStatus.BAD_REQUEST);
        }
        User user = userServices.registerUser(newUser);

        UserDto userDto = UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .lastLogin(user.getLastLogin())
                .isActive(user.getIsActive())
                .token(user.getToken()).build();
        return new ResponseEntity<Object>(userDto, HttpStatus.CREATED);

    }
}
