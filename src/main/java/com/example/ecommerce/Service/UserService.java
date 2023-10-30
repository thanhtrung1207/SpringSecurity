package com.example.ecommerce.Service;

import com.example.ecommerce.Model.DAO.UserDao;
import com.example.ecommerce.Model.User;
import com.example.ecommerce.api.model.LoginBody;
import com.example.ecommerce.api.model.RegistrationBody;
import com.example.ecommerce.exception.UserAlreadyExistsExeption;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private JWTService jwtService;

    public UserService(UserDao userDao, EncryptionService encryptionService) {
        this.userDao = userDao;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    public User registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsExeption {
        if(userDao.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()
                ||userDao.findByEmailIgnoreCase(registrationBody.getUsername()).isPresent()){
            throw new UserAlreadyExistsExeption();
        }
        User user = new User();
        user.setEmail(registrationBody.getEmail());
        user.setUsername(registrationBody.getUsername());
        user.setLastName(registrationBody.getLastName());
        user.setFirstName(registrationBody.getFirstName());
        //Todo: Encrypt password !!
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        return userDao.save(user);
    }

    public String loginUser(LoginBody loginBody){
        Optional<User> optionalUser = userDao.findByUsernameIgnoreCase(loginBody.getUsername());
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            if(encryptionService.verifyPassword(loginBody.getPassword(),user.getPassword())){
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }
}
