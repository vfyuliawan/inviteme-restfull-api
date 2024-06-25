package inviteme.restfull.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import inviteme.restfull.entiity.User;
import inviteme.restfull.model.request.RegisterUserRequest;
import inviteme.restfull.model.request.UserUpdateRequest;
import inviteme.restfull.model.response.UserResponse;
import inviteme.restfull.model.response.UserUpdateResponse;
import inviteme.restfull.model.response.WebResponse;
import inviteme.restfull.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;




@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/api/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        userService.registerUser(registerUserRequest);
        return WebResponse.<String>builder()
                .code("200")
                .message("success")
                .messageError("")
                .result("Register Berhasil")
                .build();

    }

    @GetMapping(path = "/api/users/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UserResponse> get(@RequestHeader("Authentication") String token) {
        // UserResponse userResponse = userService.getUserById(user);
        UserResponse userResponse = userService.getUserByToken(token);
        return WebResponse.<UserResponse>builder()
        .result(userResponse)
        .message("success")
        .code("00")
        .build();
    }

    @PatchMapping(path = "/api/users/current/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UserUpdateResponse> update(
                                    @RequestBody UserUpdateRequest request, 
                                    @RequestHeader("Authentication") String token) {
        UserUpdateResponse userResponse = userService.update(token, request);
        return WebResponse.<UserUpdateResponse>builder()
                    .result(userResponse)
                    .message("update success")
                    .code("00")
                    .build();
    }
    
}
