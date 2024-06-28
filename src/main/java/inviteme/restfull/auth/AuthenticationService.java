package inviteme.restfull.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import inviteme.restfull.config.JwtService;
import inviteme.restfull.entiity.Role;
import inviteme.restfull.entiity.User;
import inviteme.restfull.model.response.LoginResponse;
import inviteme.restfull.repository.UserRepository;
import inviteme.restfull.security.BCrypt;
import inviteme.restfull.service.ValidationService;
import inviteme.restfull.utility.JwtTokenUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class  AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired private ValidationService validationService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Autowired private JwtTokenUtil jwtTokenUtil;
    
    
    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        String token = jwtTokenUtil.generateToken(user.getUsername());
        String jwtToken = jwtService.generateToken(user);
        user.setToken(token);
        user.setTokenExpiredAt(System.currentTimeMillis() + (1000 * 16 * 24 * 30));
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public LoginResponse  authentication(AuthenticationRequest request) {
        validationService.validated(request);

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), 
                request.getPassword())
        );

        User userLogin = userRepository.findUserByUsername(request.getUsername())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password Salah"));

        if (BCrypt.checkpw(request.getPassword(), userLogin.getPassword())) {
            String jwtToken = jwtTokenUtil.generateToken(request.getUsername());
            userLogin.setToken(jwtToken);
            userLogin.setTokenExpiredAt(System.currentTimeMillis() + (1000 * 16 * 24 * 30));

            userRepository.save(userLogin);

            return LoginResponse.builder()
                    .token(userLogin.getToken())
                    .tokenExpiredAt(userLogin.getTokenExpiredAt())
                    .username(userLogin.getUsername())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password Salah");
        }

    }
     

}
