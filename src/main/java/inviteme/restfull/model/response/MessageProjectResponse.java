package inviteme.restfull.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

 

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageProjectResponse {
    private List<MessagesResponse> messagesRequest;
}
