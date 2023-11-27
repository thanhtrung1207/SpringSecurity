package com.example.ecommerce.Service.Impl;

import com.example.ecommerce.Repository.UserDao;
import com.example.ecommerce.Model.User;
import com.example.ecommerce.Service.EncryptionService;
import com.example.ecommerce.Service.IUserService;
import com.example.ecommerce.api.security.JWTProvider;
import com.example.ecommerce.api.model.LoginBody;
import com.example.ecommerce.api.model.RegistrationBody;
import com.example.ecommerce.exception.UserAlreadyExistsExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public  class UserService implements IUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private JWTProvider jwtProvider;





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

    public String loginUser(LoginBody loginBody) {
        Optional<User> optionalUser = userDao.findByUsernameIgnoreCase(loginBody.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                String jwtToken = jwtProvider.generateJWT(user);
                return jwtToken;
            }
        }

        return null;
    }

    @Override
    public User saveUser(User user) {
        return userDao.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userDao.delete(user);
    }


    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id).get();
    }

    @Override
    public List<User> getUsers() {
        List<User> users = userDao.findAll();
        return users;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public boolean hasUserWithUsername(String username) {
        return userDao.existsByUsername(username);
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public User validateAndGetUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
