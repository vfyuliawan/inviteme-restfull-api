package inviteme.restfull.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import inviteme.restfull.entiity.User;
import inviteme.restfull.model.request.LoginRequest;
import inviteme.restfull.model.response.LoginResponse;
import inviteme.restfull.model.response.WebResponse;
import inviteme.restfull.service.AuthService;

@RestController
public class AuthController {

    @Autowired private AuthService authService;


    @PostMapping(path = "/api/auth/login", 
                consumes = MediaType.APPLICATION_JSON_VALUE, 
                produces =  MediaType.APPLICATION_JSON_VALUE
                )
    public WebResponse<LoginResponse> login (@RequestBody LoginRequest request){
        LoginResponse login = authService.login(request);
        return WebResponse.<LoginResponse>builder()
                .result(login)
                .code("00")
                .message("Login Success")
                .build();
    }

    @DeleteMapping(path = "/api/auth/logout",  produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> logout (User user){
        authService.logout(user);
        return WebResponse.<String>builder()
                .result("Logout Success")
                .code("00")
                .message("Success")
                .build();
    }



}
