package auth.auth_files.autentification_jwt.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AuthentificationDtoReq {
    private String username;
    private String password;

}
