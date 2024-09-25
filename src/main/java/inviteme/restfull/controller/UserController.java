package inviteme.restfull.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import inviteme.restfull.entiity.User;
import inviteme.restfull.model.request.RegisterUserRequest;
import inviteme.restfull.model.request.UserInquiryRequest;
import inviteme.restfull.model.request.UserUpdateRequest;
import inviteme.restfull.model.response.UserInquiryResponse;
import inviteme.restfull.model.response.UserResponse;
import inviteme.restfull.model.response.UserUpdateResponse;
import inviteme.restfull.model.response.WebResponse;
import inviteme.restfull.service.GetUserService;
import inviteme.restfull.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;


import java.io.IOException;;

@RestController
@RequestMapping("/api/v1")

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    GetUserService getUserService;

    @PostMapping(path = "/user/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        userService.registerUser(registerUserRequest);
        return WebResponse.<String>builder()
                .code("200")
                .message("success")
                .messageError("")
                .result("Register Berhasil")
                .build();

    }

    @GetMapping(path = "/user/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UserResponse> getCurrentUser() {

        User userLogin = getUserService.getUserLogin();
        UserResponse userResponse = new UserResponse();
        userResponse.setName(userLogin.getName());
        userResponse.setAlamat(userLogin.getAlamat());
        userResponse.setEmail(userLogin.getEmail());
        userResponse.setRole(userLogin.getRole());
        userResponse.setUsername(userLogin.getUsername());
        userResponse.setPhoto(userLogin.getPhoto());
        return WebResponse.<UserResponse>builder()
                .result(userResponse)
                .message("success")
                .code("00")
                .build();
    }

    @PatchMapping(path = "/user/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UserUpdateResponse> updateUser(
            @RequestBody UserUpdateRequest request) throws IOException {
        UserUpdateResponse userResponse = userService.update(request);
        return WebResponse.<UserUpdateResponse>builder()
                .result(userResponse)
                .message("update success")
                .code("00")
                .build();
    }

    @PostMapping(path = "/user/inquiry")
    public WebResponse<UserInquiryResponse> getUserInquiry(@RequestBody UserInquiryRequest request) {
        UserInquiryResponse inquiryUser = userService.inquiryUser(request);
        return WebResponse.<UserInquiryResponse>builder()
                .result(inquiryUser)
                .message("success")
                .code("00")
                .build();
    }

}
