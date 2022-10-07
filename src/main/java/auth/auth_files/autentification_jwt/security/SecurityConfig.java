package auth.auth_files.autentification_jwt.security;


import auth.auth_files.autentification_jwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private ProviderJWT providerJWT;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() //отключает фильтр базовой аутентификации
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //мы не создаем Session в Spring тем самым не используем куки и вообще нехраним информацию о юзере в сессии (не будет создаваться фильтр, который юудет создавать сеакнс и хранить контекст)
                .and()
//                .antMatcher("/api/auth/**")
                .authorizeRequests()
//                .antMatchers(HttpMethod.GET,"/api/auth/user/*").permitAll()
                .antMatchers("/api/auth/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigure(providerJWT));
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { //AuthentificationManager указывает провайдеру извлечь данные из бд для их дальнейшего сравнения
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder(12));  //провайдер наш UserService
    }
}
