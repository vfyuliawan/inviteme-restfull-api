package inviteme.restfull.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInquiryResponse {
    private List<UserResponse> users;
    private PagingResponse paging;

}
