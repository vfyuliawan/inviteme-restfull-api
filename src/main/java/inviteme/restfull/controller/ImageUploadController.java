package inviteme.restfull.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import inviteme.restfull.model.response.GetImageStorage;
import inviteme.restfull.model.response.WebResponse;
import inviteme.restfull.service.ImageUploadService;

@RestController
@RequestMapping("/api/v1/upload")

public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping(path = "/image", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> uploadImage(@RequestBody String file) {
        try {
            GetImageStorage uploadImagetoStorage = imageUploadService.uploadImagetoStorage(file);
            var response = WebResponse.<String>builder().code("00").message("success")
                    .result(uploadImagetoStorage.getMessage()).build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            WebResponse<String> errorResponse = WebResponse.<String>builder()
                    .code("11")
                    .message("failed")
                    .messageError(e.getMessage())
                    .build();

            // Return 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
