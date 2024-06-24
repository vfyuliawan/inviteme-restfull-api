package inviteme.restfull.model.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest {


    @Size(max = 100, min = 4)
    private String name;

    @Size(max = 100, min = 4)
    private String password;

}
