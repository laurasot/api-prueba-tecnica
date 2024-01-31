package com.lauraSoto.ApiJava.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lauraSoto.ApiJava.utiles.JwtTokenUtil;

@Service
public class JwtTokenService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String generateToken(String username) {
        return jwtTokenUtil.generateToken(username);
    }
}
