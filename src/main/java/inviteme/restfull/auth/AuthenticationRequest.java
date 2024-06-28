package inviteme.restfull.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AuthenticationRequest {

     @NotBlank
    @Size(max = 100, min = 3)
    private String username;

    @NotBlank
    @Size(max = 100, min = 3)
    private String password;
}
