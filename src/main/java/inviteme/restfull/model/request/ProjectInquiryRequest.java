package inviteme.restfull.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectInquiryRequest {
    
    private String title;
    private int currentPage;
    private int size;
}

