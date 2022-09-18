package auth.auth_files.autentification_jwt.controllers;

import auth.auth_files.autentification_jwt.DTO.UserJSON_DTO;
import auth.auth_files.autentification_jwt.entity.User;
import auth.auth_files.autentification_jwt.repositories.UserRepository;
import auth.auth_files.autentification_jwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

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
        return "hello";
    }

    @PostMapping("/registrate")
    public String registration(@RequestBody UserJSON_DTO user) throws Exception {
        System.out.println(user.getUserName());
        try {
            userService.RegistrationUser(user.getUserName(), user.getPassword(), user.getRole());
        }
        catch (Exception e){
            return e.getMessage() + " регистрация не прошла:(";
        }
        return "Успешно зарегестрированны!";
    }
}
