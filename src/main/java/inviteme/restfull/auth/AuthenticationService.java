package inviteme.restfull.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

// import inviteme.restfull.config.JwtService;
import inviteme.restfull.entiity.Role;
import inviteme.restfull.entiity.User;
import inviteme.restfull.model.response.LoginResponse;
import inviteme.restfull.repository.UserRepository;
import inviteme.restfull.security.BCrypt;
import inviteme.restfull.service.GetUserService;
import inviteme.restfull.service.ValidationService;
import inviteme.restfull.utility.JwtTokenUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private ValidationService validationService;

    // private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private GetUserService getUserService;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        String token = jwtTokenUtil.generateToken(user.getUsername());
        // String jwtToken = jwtService.generateToken(user);
        user.setToken(token);
        user.setTokenExpiredAt(System.currentTimeMillis() + (1000 * 16 * 24 * 30));
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public LoginResponse authentication(AuthenticationRequest request) {
        validationService.validated(request);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        if (request.getUsername().contains("@")) {
            User userLogin = userRepository.findUserByEmail(request.getUsername())
                    .orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                                    "Username Email or Password Salah"));
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
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username Email or Password Salah");
            }
        } else {
            User userLogin = userRepository.findUserByUsername(request.getUsername())
                    .orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username Email or Password Salah"));
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
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username Email or Password Salah");
            }
        }

    }

    public Boolean cekUserLogin() {
        User userLogin = getUserService.getUserLogin();
        return userLogin.getUsername() != null ? true : false;
    }

    public Boolean logout() {
        User userLogin = getUserService.getUserLogin();
        User getUser = userRepository.findUserByToken(userLogin.getToken())
                .orElseThrow(() -> new ResponseStatusException(null));
        if (getUser.getToken() != null) {
            getUser.setToken(null);
            getUser.setTokenExpiredAt(null);
            userRepository.save(getUser);
        }

        return getUser.getUsername() != null ? true : false;
    }

}
