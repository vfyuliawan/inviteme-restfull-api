package inviteme.restfull.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import inviteme.restfull.entiity.User;
import inviteme.restfull.model.request.ProjectRequest;
import inviteme.restfull.model.response.ProjectResponse;
import inviteme.restfull.model.response.WebResponse;
import inviteme.restfull.service.ProjectService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(path = "/project/create", consumes = MediaType.MULTIPART_MIXED_VALUE
    )
    public WebResponse<ProjectResponse> createProject(
        @RequestPart("projectRequest") ProjectRequest projectRequest,
            @RequestPart("homeImage") MultipartFile homeImage,
            @RequestPart("heroImage") MultipartFile heroImage,
            @RequestPart("coverImage") MultipartFile coverImage,
            @RequestPart("akadImage") MultipartFile akadImage,
            @RequestPart("resepsiImage") MultipartFile resepsiImage,
            @RequestPart("maleInfoImage") MultipartFile maleInfoImage,
            @RequestPart("femaleInfoImage") MultipartFile femaleInfoImage
            ) {

        // Log received file names and content types
        log.info("Received heroImage: " + heroImage.getOriginalFilename() + ", Content Type: "
                + heroImage.getContentType());
        log.info("Received homeImage: " + homeImage.getOriginalFilename() + ", Content Type: "
                + homeImage.getContentType());
        log.info("Received coverImage: " + coverImage.getOriginalFilename() + ", Content Type: "
                + coverImage.getContentType());

        try {
            // Check if heroImage and homeImage are empty
            if (heroImage.isEmpty() || heroImage.isEmpty() || coverImage.isEmpty()) {
                throw new IllegalArgumentException("one of image file is empty");
            }

            // ProjectRequest projectRequest = objectMapper.readValue(projectRequestJson, ProjectRequest.class);
            ProjectResponse projectResponse = projectService.createProject(
                    projectRequest,
                    heroImage,
                    homeImage,
                    coverImage,
                    akadImage,
                    resepsiImage,
                    maleInfoImage,
                    femaleInfoImage
                    );
            return WebResponse.<ProjectResponse>builder()
                    .message("success")
                    .code("00")
                    .result(projectResponse)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse project request", e);
        }
    }
}
