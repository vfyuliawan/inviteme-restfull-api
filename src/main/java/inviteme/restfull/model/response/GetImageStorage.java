package inviteme.restfull.model.response;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetImageStorage {
    private MultipartFile file;
    private String message;
    private String fileDir;
    private String imageUrl;
}
