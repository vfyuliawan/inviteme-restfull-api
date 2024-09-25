package inviteme.restfull.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThemeExampleInquiryRequest {
    private String themeName;
    private int currentPage;
    private int size;
}
