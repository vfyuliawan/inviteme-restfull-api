package inviteme.restfull.model.response;

import java.time.LocalDateTime;
import java.util.List;

import inviteme.restfull.model.response.ProjectResponse.ThemeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectInquiryResponse {
    
    private List<GetProjectResponse> projects;
    private PagingResponse paging;

    
}
