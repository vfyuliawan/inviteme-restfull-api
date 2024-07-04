package inviteme.restfull.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

        private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        Map<String, Object> data = new HashMap<>();
        data.put("code", "401 UNAUTHORIZED");
        data.put("message", null);
        data.put("messageError", "Username or password incorrect");
        data.put("result", null);

        response.getOutputStream()
                .println(objectMapper.writeValueAsString(data));
    }
}
