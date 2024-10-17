package inviteme.restfull.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThemeCreateRequest {

    private String themeName;
    private String primaryColor;
    private String secondaryColor;
    private String textColor1;
    private String textColor2;
    private String bgImage;
    private String fgImage;


}
