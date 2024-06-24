package inviteme.restfull.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import inviteme.restfull.enumiration.ApiEnum;


@RestController
public class ImageController {


    private final String UPLOAD_DIR = ApiEnum.getUploadDir();

    @GetMapping("/api/images/{username}/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename, @PathVariable("username") String username) {
        try {
            Path userUploadDir = Paths.get(UPLOAD_DIR, username);
            Path file = userUploadDir.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read the file!", e);
        }
    }
}
