package com.example.ecommerce.Service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    @Value("${encryption.salt.round}")
    private int saltRounds;
    private String salt;

    @PostConstruct
    public void postConstruct(){
        salt = BCrypt.gensalt(saltRounds);
    }

    public String encryptPassword(String passwod){
        return BCrypt.hashpw(passwod, salt);
    }

    public boolean verifyPassword(String password, String hash){
        return BCrypt.checkpw(password,hash);
    }

}
