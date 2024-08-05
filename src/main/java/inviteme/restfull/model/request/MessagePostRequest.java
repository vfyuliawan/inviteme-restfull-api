package inviteme.restfull.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessagePostRequest {
    private String name;
    private String text;
    private String present;
    
}
