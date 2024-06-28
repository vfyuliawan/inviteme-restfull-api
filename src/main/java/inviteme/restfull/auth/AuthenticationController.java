package inviteme.restfull.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inviteme.restfull.model.response.LoginResponse;
import inviteme.restfull.model.response.WebResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));

    }

    @PostMapping("/authenticate")
    public WebResponse<LoginResponse> authentication(
            @RequestBody AuthenticationRequest request) {

        LoginResponse loginResponse = authenticationService.authentication(request);
        return WebResponse.<LoginResponse>builder()
                .result(loginResponse)
                .message("success")
                .code("00")
                .build();
    }

}
