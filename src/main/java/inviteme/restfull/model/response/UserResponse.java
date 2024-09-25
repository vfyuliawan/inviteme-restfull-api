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
public class UserResponse {

    private String username;

    private String name;

    private String alamat;

    private String email;

    private String photo;
    private Role role;


}
