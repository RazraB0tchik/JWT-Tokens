package auth.auth_files.autentification_jwt.controllers;

import auth.auth_files.autentification_jwt.DTO.AuthentificationDtoReq;
import auth.auth_files.autentification_jwt.DTO.UserJSON_DTO;
import auth.auth_files.autentification_jwt.entity.User;
import auth.auth_files.autentification_jwt.repositories.UserRepository;
import auth.auth_files.autentification_jwt.security.ProviderJWT;
import auth.auth_files.autentification_jwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Access;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/api/auth", method = RequestMethod.POST)
public class AuthentificationController {

    AuthenticationManager authenticationManager;

    @Autowired
    ProviderJWT providerJWT;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthentificationDtoReq user) {
        try {
            System.out.println("INSIDE Controller" + user.getUsername() + " " + user.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())); //ЕСЛИ ВСЕ ОКЕЙ, ТО ПОЛЬЗОВАТЕЛЮ ГЕНЕРИМ ТОКЕН
            System.out.println("inside_controller_!!!!!!!!!!!!!!! " + user.getUsername());
//            UserDetails userLogin = userService.loadUserByUsername(user.getUsername()); //ЗДЕСЬ ПРОИСХОДИТ ПРОВЕРКА ПОЛЬЗОВАТЕЛЯ В БД И ЗАТЕМ СОЗДАНИЕ USER DETAIL
            User userBD = userRepository.findUserByUserName(user.getUsername());
            if (userBD == null) {
                throw new UsernameNotFoundException("Username with name " + user.getUsername() + " not found");
            }
//            user.setActive(true);
//            System.out.println(userLogin + " !!!!!!!!!!!!!!!!!!!!!!");
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())); //ЕСЛИ ВСЕ ОКЕЙ, ТО ПОЛЬЗОВАТЕЛЮ ГЕНЕРИМ ТОКЕН
//            System.out.println("SADDDDDDDd");
            providerJWT.createToken(user.getUsername(), "User");


            HashMap<Object, Object> responseMap = new HashMap<>();
            responseMap.put("username", user.getUsername());
            responseMap.put("password", user.getPassword());
            return ResponseEntity.ok(responseMap); //ВЫВОДИМ, ЧТО ВСЕ ЗБС

        } catch (AuthenticationException e) {
            throw new BadCredentialsException(e.getMessage()); //ТЕПЕРЬ У ПОЛЬЗОВАТЕЛЯ ЕСТЬ ТОКЕН С ВРЕМЕНЕМ ЖИЗНИ И ОН МОЖЕТ ШАСТАТЬ ПО НАШЕМУ САЙТУ
        }

    }
}
