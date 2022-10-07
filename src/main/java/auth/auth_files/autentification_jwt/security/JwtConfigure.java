package auth.auth_files.autentification_jwt.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
public class JwtConfigure extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private ProviderJWT providerJWT;

    public JwtConfigure(ProviderJWT providerJWT) {
        this.providerJWT = providerJWT;
    }

    @Override
    public void configure(HttpSecurity httpSecurity){ //httpSecurity класс для настройки веб-безопасности определенных http запросов
        SecurityFilterJWT securityFilterJWT = new SecurityFilterJWT(providerJWT); //Здесь мы говорим, что наш фильтр долэжен быть создан
        httpSecurity.addFilterBefore(securityFilterJWT, UsernamePasswordAuthenticationFilter.class); //Здесь говорится что фильтр должен запускаться перед другим фильтром Spring Security
    }

}
