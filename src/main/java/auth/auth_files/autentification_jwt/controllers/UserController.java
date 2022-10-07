package auth.auth_files.autentification_jwt.controllers;

import auth.auth_files.autentification_jwt.DTO.UserJSON_DTO;
import auth.auth_files.autentification_jwt.entity.User;
import auth.auth_files.autentification_jwt.repositories.UserRepository;
import auth.auth_files.autentification_jwt.security.ProviderJWT;
import auth.auth_files.autentification_jwt.security.SecurityFilterJWT;
import auth.auth_files.autentification_jwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user", method = RequestMethod.GET)
public class UserController {

    AuthenticationManager authenticationManager;

    @Autowired
    ProviderJWT providerJWT;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/show_users")
    public List<User> returnUser(){
        return userRepository.findAll();
    }

    @GetMapping("/check")
    public String test(){
        System.out.println("im inside controller");
        System.out.println("token: " + providerJWT.createToken("Ivan", "User"));
        return "hello";
    }


//    @PostMapping("/registration")
//    public String registration(@RequestBody UserJSON_DTO user){
//        System.out.println(user.getUserName() + " adafsdfs");
//        try {
//            userService.RegistrationUser(user.getUserName(), user.getPassword(), user.getRole(), user.getActive());
//            providerJWT.createToken(user.getUserName(), user.getRole());
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
//        }
//
//
//        catch (Exception e){
//            return e.getMessage() + " регистрация не прошла:(";
//        }
//        return "Успешно зарегестрированны!";
//    }

}
