package inviteme.restfull.auth;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Size(max = 100)
    private String username;

    @Size(max = 100)
    private String password;

    @Size(max = 100)
    private String name;

}
