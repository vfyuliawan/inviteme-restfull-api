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
import org.springframework.web.bind.annotation.RequestPart;

@Slf4j
@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/api/project", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ProjectResponse> createProject(@RequestPart("projectRequest") String projectRequestJson,
            @RequestPart("homeImage") MultipartFile homeImage,
            @RequestPart("heroImage") MultipartFile heroImage,
            @RequestPart("coverImage") MultipartFile coverImage,
            @RequestPart("akadImage") MultipartFile akadImage,
            @RequestPart("resepsiImage") MultipartFile resepsiImage,
            User user) {

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

            ProjectRequest projectRequest = objectMapper.readValue(projectRequestJson, ProjectRequest.class);
            ProjectResponse projectResponse = projectService.createProject(user,
                    projectRequest,
                    heroImage,
                    homeImage,
                    coverImage,
                    akadImage,
                    resepsiImage);
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
