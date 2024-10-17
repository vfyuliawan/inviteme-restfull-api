package inviteme.restfull.model.response;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DescriptionsInquiryResponse {
    private List<DescriptionsResponse> descriptions;
    private PagingResponse paging;
}
