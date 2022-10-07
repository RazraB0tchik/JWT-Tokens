package auth.auth_files.autentification_jwt.security;


import auth.auth_files.autentification_jwt.services.UserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class ProviderJWT {

    private UserDetailsService userDetailsService; //ЭТОТ КЛАСС ОТВЕЧАЕТ ЗА СОЗДАНИЕ И ПРОВЕРКУ ТОКЕНОВ

    @Value(value = "${jwt.token.secret}")
    String securityKey;
    @Value(value = "${jwt.token.expired}")
    long tokenExpired;

    @PostConstruct
    protected void init(){
        securityKey= Base64.getEncoder().encodeToString(securityKey.getBytes());
    }

    public String createToken(String username, String role){

        Claims claims = Jwts.claims().setSubject(username); //запихиваем наши полезные данные
        claims.put("role", role); //данные запихиваются как ключ значение, json

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenExpired); //для того чтобы мы могли знать когда истечет время токена, необходимо к дате его создания прибавить время жизни

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) //с какого времени начнется отчет жизненного цикла
                .setExpiration(validity) //по какое время будет жить токен
                .signWith(SignatureAlgorithm.HS256, securityKey) //подпись, указываем алгоритм хеширования и ключ
                .compact();

    }

    public Authentication authenticationToken(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(getSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()); //данный экземпляр возвращает имя пользователя и пароль в authentification
    }

    public String getSubject(String token){
        return Jwts.parser().setSigningKey(securityKey).parseClaimsJws(token).getBody().getSubject(); //parser читает json, и при помощи ключа выдергивает субъект
    }

    public String resolve (HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization"); //этот метод ищет токен в запросе
        if((bearerToken != null) && (bearerToken.startsWith("Bearer_"))){
            return bearerToken.substring(7, bearerToken.length()); //проверка хедера на тип
        }
        return null;
    }

    public boolean validate(String token){
        try{
            Jws<Claims> claim = Jwts.parser().setSigningKey(securityKey).parseClaimsJws(token);

            if(claim.getBody().getExpiration().before(new Date())){ //метод получения срока действия, он до текущего времени
                return false;
            }
            return true;
        }
        catch (JwtException | IllegalArgumentException e){
            throw new JwtException("Invalid token");
        }
    }



}
