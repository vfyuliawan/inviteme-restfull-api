package inviteme.restfull.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import inviteme.restfull.repository.ProjectRepositry;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class ProjectControllerImageTest {
        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private ProjectRepositry projectRepositry;

        // @BeforeEach
        // void setUp() {
        //     projectRepositry.deleteAll();
        // }

        @Test
        void deleteAllProjcet(){
                        // projectRepositry.deleteAll();

        }
}
