package auth.auth_files.autentification_jwt.entity;

import auth.auth_files.autentification_jwt.services.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import javax.transaction.Status;
import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails { //здесь писанно все для user detail элемента, по сути это тот же pojo класс, при помощи него мы преобразуем нашего user в user detail в котором описаны права
    private final String userName;
    private final String userPassword;
    private final String active;
    private final List<SimpleGrantedAuthority> authorities;

    public SecurityUser(String userName, String userPassword, String userActive, List<SimpleGrantedAuthority> authorities) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.active = userActive;
        this.authorities = authorities;
    }
//
//    public SecurityUser(String userName, String userPassword, String userActive, List<GrantedAuthority> authorities) {
//
//    }

    @Override    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    public UserDetails fromStUser(User user){
//        System.out.println("inside security User!");
//        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(), user.getUserActive().equals(StatusUser.Status.ACTIVE),  user.getUserActive().equals(StatusUser.Status.ACTIVE),  user.getUserActive().equals(StatusUser.Status.ACTIVE),  user.getUserActive().equals(StatusUser.Status.ACTIVE), userService.getAuthorities(user));
//    }
}
