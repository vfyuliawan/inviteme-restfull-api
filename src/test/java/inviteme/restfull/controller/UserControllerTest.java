package inviteme.restfull.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import inviteme.restfull.model.request.RegisterUserRequest;
import inviteme.restfull.model.request.UserUpdateRequest;
import inviteme.restfull.model.response.UserResponse;
import inviteme.restfull.model.response.UserUpdateResponse;
import inviteme.restfull.model.response.WebResponse;
import inviteme.restfull.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        //  userRepository.deleteAll();


        // for (int i = 0; i < 5; i++) {
        //     User user = new User();
        //     user.setUsername("cang"+i);
        //     user.setName("cang"+i);
        //     user.setPassword(BCrypt.hashpw("testPassword", BCrypt.gensalt()));
        //     userRepository.save(user);
        // }

    }

    @Test
    void testRegisterUser_Success() throws Exception {
       RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("cang");
        registerUserRequest.setPassword("rahasia");
        registerUserRequest.setName("test");

        mockMvc.perform(post("/api/users")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registerUserRequest)))
            .andExpectAll(
                    status().isOk())
            .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                                    new TypeReference<>() {
                                    });
                    assertEquals("200", response.getCode());
                    assertTrue(userRepository.existsById("cang"));
                    log.info("RESPONSE {}" , response);
                });
    }

    @Test
    void testRegisterUser_Failed() throws Exception {

       
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("");
        registerUserRequest.setPassword("");
        registerUserRequest.setName("");

        mockMvc.perform(post("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerUserRequest)))
                .andExpectAll(
                        status().isBadRequest())
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    assertNotNull(response.getMessageError());
                    log.info("RESPONSE {}", response);
                });
    }


    @Test
    void getUser_success() throws Exception {

        mockMvc.perform(get("/api/users/current")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "b099319d-167b-43d9-b267-cde78b4e308e"))
                .andExpectAll(
                        status().isOk())
                .andDo(result -> {
                    WebResponse<UserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    log.info("RESPONSE {}", response);
                });
    }

    @Test
    void getUser_notfound() throws Exception {

        mockMvc.perform(get("/api/users/current")
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isUnauthorized())
                .andDo(result -> {
                    WebResponse<UserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    log.info("RESPONSE {}", response);
                });
    }

     @Test
    void updateUser_success() throws Exception{
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setName("cang edited");
        userUpdateRequest.setPassword("cang123");

        mockMvc.perform(patch("/api/users/current")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userUpdateRequest))
        .header("Authorization", "b22dbade-ffb3-4424-aa50-a0e6fcf34716")
        ).andExpectAll(
            status().isOk()
        ).andDo(
            result -> {
                WebResponse<UserUpdateResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() { 
                });

                log.info("RESPONSE {}", response);
            }
        );
    }

    @Test
    void updateUser_failed() throws Exception{
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setName("cang edited");
        userUpdateRequest.setPassword("cang123");

        mockMvc.perform(patch("/api/users/current")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userUpdateRequest))
        .header("Authorization", "b22dbade-ffb3-4424-aa50-")
        ).andExpectAll(
            status().isUnauthorized()
        ).andDo(
            result -> {
                WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() { 
                });

                log.info("RESPONSE {}", response);
            }
        );
    }

    
}

