package inviteme.restfull.model.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThemeExampleResponse {

    private String id;
    private String themeName;
    private String primaryColor;
    private String secondaryColor;
    private String textColor1;
    private String textColor2;
    private String bgImage;
    private String fgImage;
    private LocalDateTime createdAt;
}
