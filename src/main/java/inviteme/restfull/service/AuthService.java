package inviteme.restfull.service;

import java.util.UUID;

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

@Service
public class AuthService {
    @Autowired private UserRepository userRepository;

    @Autowired private ValidationService validationService;


    public LoginResponse login(LoginRequest request){
        validationService.validated(request);
        User userLogin = userRepository.findById(request.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password Salah"));

        if (BCrypt.checkpw(request.getPassword(), userLogin.getPassword())) {
            userLogin.setToken(UUID.randomUUID().toString());
            userLogin.setTokenExpiredAt(nextExpiredDate());

            userRepository.save(userLogin);

            return LoginResponse.builder()
                    .token(userLogin.getToken())
                    .tokenExpiredAt(userLogin.getTokenExpiredAt())
                    .build();
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password Salah");
        }
    }

    private Long nextExpiredDate() {
        return System.currentTimeMillis() + (1000 * 16 * 24 * 30);
    }

    @Transactional
    public void logout(User user){
        user.setToken(null);
        user.setTokenExpiredAt(null);
        userRepository.save(user);
    }
}
