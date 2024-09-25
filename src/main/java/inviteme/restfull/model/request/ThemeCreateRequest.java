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
    private String themeColor;
    private String bgImage;
    private String fgImage;


}
