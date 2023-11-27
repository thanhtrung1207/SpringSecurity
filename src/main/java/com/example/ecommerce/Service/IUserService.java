package com.example.ecommerce.Service;

import com.example.ecommerce.Model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface IUserService extends UserDetailsService {


    User saveUser(User user);

    void deleteUser(User user);

    List<User> findAll();

    User findById(Long id);

    List<User> getUsers();

    Optional<User> getUserByUsername(String username);

    boolean hasUserWithUsername(String username);

    boolean hasUserWithEmail(String email);

    User validateAndGetUserByUsername(String username);
}
