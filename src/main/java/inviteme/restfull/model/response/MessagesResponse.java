package inviteme.restfull.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessagesResponse {
    private String messageId;
    private String name;
    private String text;
    private String present;
    private LocalDateTime time;
}
