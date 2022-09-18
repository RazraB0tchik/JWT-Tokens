package auth.auth_files.autentification_jwt.services;

import auth.auth_files.autentification_jwt.entity.User;
import auth.auth_files.autentification_jwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserName(username);

        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }
        else{
            return (UserDetails) user;
        }
    }

    public void RegistrationUser(String newuser_name, String password, String role) throws Exception {
        User user = userRepository.findUserByUserName(newuser_name);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        if(user!=null){
            throw new Exception("User with this username already have!");
        }
        else{
            userRepository.save(new User(newuser_name, encoder.encode(password), role));
        }
    }
}
