package inviteme.restfull.service;

// import java.util.Optional;
// import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import inviteme.restfull.entiity.User;
import inviteme.restfull.model.request.LoginRequest;
import inviteme.restfull.model.response.LoginResponse;
import inviteme.restfull.repository.UserRepository;
import inviteme.restfull.security.BCrypt;
import inviteme.restfull.utility.JwtTokenUtil;
import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

// import javax.crypto.SecretKey;

@Service

@Slf4j
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public LoginResponse login(LoginRequest request) {
        validationService.validated(request);

        User userLogin = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password Salah"));

        if (BCrypt.checkpw(request.getPassword(), userLogin.getPassword())) {
            String jwtToken = jwtTokenUtil.generateToken(request.getUsername());
            userLogin.setToken(jwtToken);
            userLogin.setTokenExpiredAt(nextExpiredDate());

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

    private Long nextExpiredDate() {
        return System.currentTimeMillis() + (1000 * 16 * 24 * 30);
    }

    @Transactional
    public void logout(User user) {
        user.setToken(null);
        user.setTokenExpiredAt(null);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User cekUserByToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token");
        }
        try {
            String jwtToken = token.substring(7);
            Claims claims = jwtTokenUtil.parseToken(jwtToken);
            String username = claims.getSubject();
            long expirationTimeMillis = claims.getExpiration().getTime();
            log.info("Claims: {}", claims);
            log.info("Username: {}", username);
            log.info("Expiration Time (Millis): {}", expirationTimeMillis);
            if (expirationTimeMillis < System.currentTimeMillis()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Expired Token");
            }
            User user = userRepository.findUserByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));

            return user;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.toString());
        }

    }
}
