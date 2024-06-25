package inviteme.restfull.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import inviteme.restfull.entiity.User;
import inviteme.restfull.model.request.RegisterUserRequest;
import inviteme.restfull.model.request.UserUpdateRequest;
import inviteme.restfull.model.response.UserResponse;
import inviteme.restfull.model.response.UserUpdateResponse;
import inviteme.restfull.repository.UserRepository;
import inviteme.restfull.security.BCrypt;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired private AuthService authService;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void registerUser(RegisterUserRequest registerUserRequest) {
        log.info("REQUEST SERVICE {}", registerUserRequest);
        validationService.validated(registerUserRequest);

        if (userRepository.existsById(registerUserRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username Already Registered");
        }

        User user = new User();
        user.setUsername(registerUserRequest.getUsername());
        user.setPassword(BCrypt.hashpw(registerUserRequest.getPassword(), BCrypt.gensalt()));
        user.setName(registerUserRequest.getName());
        userRepository.save(user);
    }


    @Transactional(readOnly = true)
    public UserResponse getUserById(User user){
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())        
                .build();
    }


    @Transactional(readOnly = true)
    public UserResponse getUserByToken(String token){
        User user = authService.cekUserByToken(token);
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())        
                .build();
    }

    @Transactional
    public UserUpdateResponse update(String token, UserUpdateRequest request){
       validationService.validated(request);
       User user = authService.cekUserByToken(token);
       if (Objects.nonNull(request.getName())) {
            user.setName(request.getName());
       } 

       if (Objects.nonNull(request.getPassword())) {
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
       }

        userRepository.save(user);
        return UserUpdateResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .build();

    }


}
