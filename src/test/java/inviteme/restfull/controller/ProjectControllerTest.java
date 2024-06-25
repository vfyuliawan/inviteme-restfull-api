// package inviteme.restfull.controller;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import java.time.LocalDateTime;

// import com.fasterxml.jackson.core.type.TypeReference;
// import com.fasterxml.jackson.databind.ObjectMapper;

// import inviteme.restfull.model.request.ProjectRequest;
// import inviteme.restfull.model.response.ProjectResponse;
// import inviteme.restfull.model.response.WebResponse;
// import lombok.extern.slf4j.Slf4j;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.BeforeEach;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.mock.web.MockMultipartFile;
// import org.springframework.test.web.servlet.MockMvc;

// @SpringBootTest
// @Slf4j
// @AutoConfigureMockMvc
// public class ProjectControllerTest {
//         @Autowired
//         private MockMvc mockMvc;

//         @Autowired
//         private ObjectMapper objectMapper;

//         @BeforeEach
//         void setUp() {
//         }

//         @Test
//         void testRegisterProjectWithImage_Success() throws Exception {
//                 ProjectRequest projectRequest = ProjectRequest.builder()
//                                 .title("Test Project")
//                                 .hero(ProjectRequest.HeroRequest.builder()
//                                                 .title("Hero Title")
//                                                 .date(LocalDateTime.now())
//                                                 .isShow(true)
//                                                 .build()) 
//                                 .build();

//                 // Serialize ProjectRequest to JSON
//                 String projectRequestJson = objectMapper.writeValueAsString(projectRequest);

//                 // Create MockMultipartFile for the image
//                 MockMultipartFile imageFile = new MockMultipartFile("image", "hero-image.jpg",
//                                 MediaType.IMAGE_JPEG_VALUE,
//                                 new byte[] { 1, 2, 3, 4 });

//                 // Create MockMultipartFile for the JSON part
//                 MockMultipartFile jsonFile = new MockMultipartFile("projectRequest", "",
//                                 MediaType.APPLICATION_JSON_VALUE, projectRequestJson.getBytes());

//                 // Perform the multipart request
//                 mockMvc.perform(multipart("/api/project")
//                                 .file(imageFile)
//                                 .file(jsonFile)
//                                 .header("Authorization", "f8ed24e4-1e4e-4eee-951b-a5b549a55461")
//                                 .contentType(MediaType.MULTIPART_FORM_DATA))
//                                 .andExpect(status().isOk())
//                                 .andDo(result -> {
//                                         WebResponse<ProjectResponse> response = objectMapper.readValue(
//                                                         result.getResponse().getContentAsString(),
//                                                         new TypeReference<WebResponse<ProjectResponse>>() {
//                                                         });
//                                         log.info("RESPONSE {}", response);
//                                 });
//         }

// }
