package auth.auth_files.autentification_jwt.services;

import auth.auth_files.autentification_jwt.entity.SecurityUser;
import auth.auth_files.autentification_jwt.entity.User;
import auth.auth_files.autentification_jwt.repositories.UserRepository;
import auth.auth_files.autentification_jwt.security.CreateSecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserService implements UserDetailsService { //DAO Auth Manager вызывает User Det Ser для проверки пользователя в базе

    @Autowired
    UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user =  userRepository.findUserByUserName(username);
        System.out.println(user.getUserName() + " inside Service");


        System.out.println("ABOBA");
        SecurityUser securityUser = CreateSecurityUser.create(user); //если есть мы наделяем юзера правами , создаем и возвращаем user detail, который затем полетит в контекст
        log.info("Success");
        return securityUser;
    }


//    public Collection getAuthorities(User user){
//
//        String userRole = user.getUserRole();
//
//        authoroties.add(new SimpleGrantedAuthority(userRole));
//
//        return authoroties;
//    }

//    public void RegistrationUser(String newuser_name, String password, String role, String active) throws Exception {
//        User user = userRepository.findUserByUserName(newuser_name);
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
//        if(user!=null){
//            throw new Exception("User with this username already have!");
//        }
//        else{
//            userRepository.save(new User(newuser_name, encoder.encode(password), role, active));
//        }
//    }

//    @Override
//    public User findUser(String username) {
//        User user = userRepository.findUserByUserName(username);
//
//        if(user==null){
//            throw new UsernameNotFoundException("User not found");
//        }
//        else{
//            return user;
//        }
//    }
//
//    @Override
//    public ResponseEntity registration(User user) {
//        User userRegistration = userRepository.findUserByUserName(user.getUserName());
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
//        if(userRegistration!=null){
//            throw new UsernameNotFoundException("User with username "+ user.getUserName() +" not found!");
//        }
//        else{
//            userRepository.save(new User(user.getUserName(), encoder.encode(user.getUserPassword()), user.getUserRole(), user.getUserActive()));
//        }
//        HashMap<Object, Object> goofRegistStatus = new HashMap<>();
//        goofRegistStatus.put("userName", user.getUserName());
//        return ResponseEntity.ok(goofRegistStatus);
//    }
}
