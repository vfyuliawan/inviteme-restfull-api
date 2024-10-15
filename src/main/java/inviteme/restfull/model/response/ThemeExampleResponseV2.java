package inviteme.restfull.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ThemeExampleResponseV2 {
    private String themeName;
    private String primaryColor;
    private String secondaryColor;
}
