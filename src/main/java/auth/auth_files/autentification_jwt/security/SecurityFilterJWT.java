package auth.auth_files.autentification_jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class SecurityFilterJWT implements Filter {

    @Autowired
    ProviderJWT providerJWT;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);

        //выполняем логику инициализации фильтра
    }

    public SecurityFilterJWT(ProviderJWT providerJWT) {
        this.providerJWT = providerJWT;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException { //принимаем запрос с токеном

        String tokenRequest = providerJWT.resolve((HttpServletRequest) servletRequest); //потрошим запрос и смотрим есть ли в нем токен
        System.out.println(tokenRequest + " asdasd");
        if(tokenRequest != null && providerJWT.validate(tokenRequest)){
            Authentication auth = providerJWT.authenticationToken(tokenRequest);// если токен валиден и все збс, берем из токена ауетнтификацию

            if(auth!=null) {
                SecurityContextHolder.getContext().setAuthentication(auth); //заменяем текущий контекст пользователя на аутентифицированного
            }
        }

        filterChain.doFilter(servletRequest, servletResponse); //по сути наш написанный фильтр находится в иерархти фильтров сприга, и по сути вся логика должна выполняться до filterChain. FilterChain говорит приложению, что все, можно переходить к следующему фильтру в Spring

    }

    @Override
    public void destroy() {
        //выполняем логику до уничтожения фильтра
        Filter.super.destroy();
    }
}
