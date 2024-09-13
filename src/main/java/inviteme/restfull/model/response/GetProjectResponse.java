package inviteme.restfull.model.response;

import java.time.LocalDateTime;

import inviteme.restfull.model.response.ProjectResponse.ThemeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProjectResponse {
    private String id;
    private String username;
    private String title;
    private LocalDateTime date;
    private LocalDateTime publishDate;
    private ThemeResponse theme;
}
