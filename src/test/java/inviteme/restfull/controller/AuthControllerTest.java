package inviteme.restfull.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import inviteme.restfull.model.request.LoginRequest;
import inviteme.restfull.model.response.LoginResponse;
import inviteme.restfull.model.response.WebResponse;
import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        
    }

    @Test
    void login_success () throws Exception{

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("cang1");
        loginRequest.setPassword("testPassword");

        String writeValueAsString = objectMapper.writeValueAsString(loginRequest);
        log.info("REQUEST {}", writeValueAsString);
        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpectAll(
                        status().isOk())
                .andDo(result -> {
                    WebResponse<LoginResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    log.info("RESPONSE {}" , response);
                });
    }

    @Test
    void login_failed () throws Exception{

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("cang");
        loginRequest.setPassword("testPassword123");

        
        mockMvc.perform(
                post("/api/auth/login").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpectAll(
                        status().isUnauthorized())
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    log.info("RESPONSE {}" , response);
                    assertNotNull(response.getMessageError());
                });
    }


    @Test

    void logout_failed() throws Exception
    {
        mockMvc.perform(delete("/api/auth/logout")
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                    status().isUnauthorized()
                ).andDo(
                    result ->{
                        WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    log.info("RESPONSE {}" , response);
                    assertNotNull(response.getMessageError());
                    } 
                );
    }


    @Test

    void logout_success() throws Exception
    {
        mockMvc.perform(delete("/api/auth/logout")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "4724511b-04e3-43a9-a441-132618018065"))
                .andExpectAll(
                    status().isOk()
                ).andDo(
                    result ->{
                        WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    log.info("RESPONSE {}" , response);
                    assertNull(response.getMessageError());;
                    } 
                );
    }


}
