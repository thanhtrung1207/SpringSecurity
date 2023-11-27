package com.example.ecommerce.api.controller.auth;

import com.example.ecommerce.Model.User;
import com.example.ecommerce.Service.Impl.UserService;
import com.example.ecommerce.api.model.LoginBody;
import com.example.ecommerce.api.model.LoginRespone;
import com.example.ecommerce.api.model.RegistrationBody;
import com.example.ecommerce.exception.UserAlreadyExistsExeption;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {

    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody){
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        }catch (UserAlreadyExistsExeption ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }
    @PostMapping("/login")
    public ResponseEntity<LoginRespone> loginUser(@Valid @RequestBody LoginBody loginBody) {
        String jwt = String.valueOf(userService.loginUser(loginBody));

        if (jwt == null) {
            return ResponseEntity.badRequest().build();
        } else {
            LoginRespone response = new LoginRespone();
            response.setJwt(jwt);
            return ResponseEntity.ok(response);
        }
    }
    @GetMapping("/me")
    public User getLoggedInUserProfile(@AuthenticationPrincipal User user){
        return user;
    }

}
