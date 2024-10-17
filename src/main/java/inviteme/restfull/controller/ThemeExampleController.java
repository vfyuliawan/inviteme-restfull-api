package inviteme.restfull.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inviteme.restfull.model.request.ThemeCreateRequest;
import inviteme.restfull.model.request.ThemeExampleInquiryRequest;
import inviteme.restfull.model.response.ThemeExampleResponse;
import inviteme.restfull.model.response.ThemeExampleResponseInquiry;
import inviteme.restfull.model.response.WebResponse;
import inviteme.restfull.service.ThemeExampleService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1")
public class ThemeExampleController {

    @Autowired
    private ThemeExampleService themeExampleService;

    @GetMapping(path = "/theme/inquiry")
    public WebResponse<ThemeExampleResponseInquiry> getThemeInquiry(
            @RequestParam(required = false) String themeName,
            @RequestParam(required = false) int currentPage,
            @RequestParam(required = false) int pageSize
            )
            throws IOException {
        try {
            ThemeExampleInquiryRequest request = ThemeExampleInquiryRequest.builder().currentPage(currentPage)
                    .themeName(themeName).size(pageSize).build();
            ThemeExampleResponseInquiry thmeInquiry = themeExampleService.getThmeInquiry(request);
            return WebResponse.<ThemeExampleResponseInquiry>builder()
                    .code("00")
                    .message("success")
                    .result(thmeInquiry)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(path = "/theme/create")
    public WebResponse<ThemeExampleResponse> createThemeExample(@RequestBody ThemeCreateRequest request)
            throws IOException {
        try {
            ThemeExampleResponse themeResponse = themeExampleService.createTheme(request);
            return WebResponse.<ThemeExampleResponse>builder()
                    .code("00")
                    .message("success")
                    .result(themeResponse)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.toString());

        }

    }

    @DeleteMapping(path = "/theme/delete")
    public ResponseEntity<WebResponse<Boolean>> deleteTheme(@RequestParam String id) throws IOException {
        try {
            Boolean deleteTheme = themeExampleService.deleteTheme(id);
            if (deleteTheme) {
                WebResponse<Boolean> response = WebResponse.<Boolean>builder().code("00").message("success")
                        .result(deleteTheme).build();
                return ResponseEntity.ok(response);

            } else {
                WebResponse<Boolean> response = WebResponse.<Boolean>builder()
                        .code("401")
                        .messageError("id theme not found")
                        .build();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

}
