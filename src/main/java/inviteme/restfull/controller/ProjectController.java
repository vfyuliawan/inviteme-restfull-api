package inviteme.restfull.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import inviteme.restfull.model.request.ProjectInquiryRequest;
import inviteme.restfull.model.request.ProjectRequest;
import inviteme.restfull.model.response.ProjectInquiryResponse;
import inviteme.restfull.model.response.ProjectResponse;
import inviteme.restfull.model.response.WebResponse;
import inviteme.restfull.service.ProjectService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ProjectController {

        @Autowired
        private ProjectService projectService;

        @PostMapping(path = "/project/create", consumes = MediaType.MULTIPART_MIXED_VALUE)
        public WebResponse<ProjectResponse> createProject(
                        @RequestPart("projectRequest") ProjectRequest projectRequest,
                        @RequestPart("homeImage") MultipartFile homeImage,
                        @RequestPart("heroImage") MultipartFile heroImage,
                        @RequestPart("coverImage") MultipartFile coverImage,
                        @RequestPart("akadImage") MultipartFile akadImage,
                        @RequestPart("resepsiImage") MultipartFile resepsiImage,
                        @RequestPart("maleInfoImage") MultipartFile maleInfoImage,
                        @RequestPart("femaleInfoImage") MultipartFile femaleInfoImage) {

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

                        // ProjectRequest projectRequest = objectMapper.readValue(projectRequestJson,
                        // ProjectRequest.class);
                        ProjectResponse projectResponse = projectService.createProject(
                                        projectRequest,
                                        heroImage,
                                        homeImage,
                                        coverImage,
                                        akadImage,
                                        resepsiImage,
                                        maleInfoImage,
                                        femaleInfoImage);
                        return WebResponse.<ProjectResponse>builder()
                                        .message("success")
                                        .code("00")
                                        .result(projectResponse)
                                        .build();
                } catch (IOException e) {
                        throw new RuntimeException("Failed to parse project request", e);
                }
        }

        @PostMapping(path = "/project/createNew", consumes = MediaType.APPLICATION_JSON_VALUE)
        public WebResponse<ProjectResponse> createNewProject(
                        @RequestBody ProjectRequest projectRequest) {

                try {
                        ProjectResponse projectResponse = projectService.createNewProject(
                                        projectRequest);
                        return WebResponse.<ProjectResponse>builder()
                                        .message("success")
                                        .code("00")
                                        .result(projectResponse)
                                        .build();
                } catch (IOException e) {
                        throw new RuntimeException("Failed to parse project request", e);
                }
        }

        @PostMapping(path = "/project/inquiry", consumes = MediaType.APPLICATION_JSON_VALUE)
        public WebResponse<ProjectInquiryResponse> getInquiryProject(
                        @RequestBody ProjectInquiryRequest projectRequest) {
                ProjectInquiryResponse projectInquiry = projectService.projectInquiry(projectRequest);
                return WebResponse.<ProjectInquiryResponse>builder()
                                .message("success")
                                .code("00")
                                .result(projectInquiry)
                                .build();

        }

        @GetMapping(path = "/project/myProjects")
        public WebResponse<ProjectInquiryResponse> getMyProjects(
                        @RequestParam int currentPage,
                        @RequestParam int size,
                        @RequestParam(required = false) String title) {
                ProjectInquiryRequest projectRequest = new ProjectInquiryRequest();
                projectRequest.setTitle(title != null ? title : "");
                projectRequest.setCurrentPage(currentPage);
                projectRequest.setSize(size);

                ProjectInquiryResponse projectInquiry = projectService.getMyProject(projectRequest);
                return WebResponse.<ProjectInquiryResponse>builder()
                                .message("success")
                                .code("00")
                                .result(projectInquiry)
                                .build();
        }

        @GetMapping(path = "/project/get")
        public WebResponse<ProjectResponse> getProjectById(
                        @RequestParam String id) {
                ProjectResponse projectResponse = projectService.getProjectById(id);
                return WebResponse.<ProjectResponse>builder()
                                .message("success")
                                .code("00")
                                .result(projectResponse)
                                .build();
        }

        @GetMapping(path = "/project/getBySlug")
        public WebResponse<ProjectResponse> getProjectBySlugAndTheme(
                        @RequestParam String slug) {
                ProjectResponse projectResponse = projectService.getProjectBySlugAndTheme(slug);
                return WebResponse.<ProjectResponse>builder()
                                .message("success")
                                .code("00")
                                .result(projectResponse)
                                .build();
        }

        @PatchMapping(path = "/project/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public WebResponse<ProjectResponse> update(
                        @RequestBody ProjectRequest projectRequest,
                        @RequestParam String idProject) {
                try {
                        ProjectResponse projectResponse = projectService.updateProject(idProject, projectRequest);
                        return WebResponse.<ProjectResponse>builder()
                                        .message("success")
                                        .code("00")
                                        .result(projectResponse)
                                        .build();
                } catch (IOException e) {
                        throw new RuntimeException("Failed to parse project request", e);
                }
        }

        @DeleteMapping(path = "project/delete", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<WebResponse<String>> delete(
                        @RequestParam String id) {
                try {
                        String deleteProject = projectService.deleteProject(id);
                        WebResponse<String> response = WebResponse.<String>builder()
                                        .code("00")
                                        .message("success")
                                        .result(deleteProject)
                                        .build();
                        return ResponseEntity.ok(response);
                } catch (Exception e) {
                        WebResponse<String> response = WebResponse.<String>builder()
                                        .code("55")
                                        .messageError(e.toString())
                                        .build();
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
        }
}
