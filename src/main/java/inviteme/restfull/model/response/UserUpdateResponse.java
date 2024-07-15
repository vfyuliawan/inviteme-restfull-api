package inviteme.restfull.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateResponse {
    
    private String username;

    private String name;

    private String alamat;

    private String email;

    private String photo;

    
}
