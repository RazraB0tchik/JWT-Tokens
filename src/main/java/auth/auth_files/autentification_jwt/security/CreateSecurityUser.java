package auth.auth_files.autentification_jwt.security;

import auth.auth_files.autentification_jwt.entity.SecurityUser;
import auth.auth_files.autentification_jwt.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CreateSecurityUser {
    public CreateSecurityUser() {
    }
    public static SecurityUser create (User user){ //здесь мы создаем нашего jwt user, то есть создаем user detail объект, который возвращает права пользователя
        return new SecurityUser(user.getUserName(), user.getUserPassword(), user.getUserActive(), authorities(user.getUserRole()));
    }
    static List<SimpleGrantedAuthority>  authoritiList = new ArrayList();

       private static List<SimpleGrantedAuthority> authorities (String userRole){
           authoritiList.add(new SimpleGrantedAuthority(userRole));
           return authoritiList;
         }

}
