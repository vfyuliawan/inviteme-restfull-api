package inviteme.restfull.model.response;

import inviteme.restfull.entiity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    private String username;
    
    private String token;

    private Long tokenExpiredAt;

    private Role role;


}
