package inviteme.restfull.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import inviteme.restfull.entiity.User;
import inviteme.restfull.model.request.RegisterUserRequest;
import inviteme.restfull.model.request.UserInquiryRequest;
import inviteme.restfull.model.request.UserUpdateRequest;
import inviteme.restfull.model.response.GetImageStorage;
import inviteme.restfull.model.response.PagingResponse;
import inviteme.restfull.model.response.UserInquiryResponse;
import inviteme.restfull.model.response.UserResponse;
import inviteme.restfull.model.response.UserUpdateResponse;
import inviteme.restfull.repository.UserRepository;
import inviteme.restfull.security.BCrypt;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;



import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private GetUserService getUserService;

    @Autowired
    private ImageUploadService imageUploadService;

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
        user.setEmail(registerUserRequest.getEmail());
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByToken(String token) {
        User user = authService.cekUserByToken(token);
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }

    @Transactional
    public UserUpdateResponse update(UserUpdateRequest request) throws IOException {
        validationService.validated(request);
        User user = getUserService.getUserLogin();
        if (Objects.nonNull(request.getName())) {
            user.setName(request.getName());
        }

        if (Objects.nonNull(request.getPassword())) {
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        if (Objects.nonNull(request.getEmail())) {
            user.setEmail(request.getEmail());
        }

        if (Objects.nonNull(request.getAlamat())) {
            user.setAlamat(request.getAlamat());
        }

        if (Objects.nonNull(request.getPhoto())) {
            if (request.getPhoto().contains("https")) {
                user.setPhoto(request.getPhoto());
            }else{
                if (!Objects.nonNull(user.getPhoto())) {
                    GetImageStorage userPhoto = imageUploadService.uploadImagetoStorage(request.getPhoto());
                    user.setPhoto(userPhoto.getImageUrl());
                }else{
                    imageUploadService.deleteImageStorage(user.getPhoto());
                    GetImageStorage userPhoto = imageUploadService.uploadImagetoStorage(request.getPhoto());
                    user.setPhoto(userPhoto.getImageUrl());
                }
              

            }
        }

        userRepository.save(user);
        return UserUpdateResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .alamat(user.getAlamat())
                .email(user.getEmail())
                .photo(user.getPhoto())
                .build();

    }

    public UserInquiryResponse inquiryUser (UserInquiryRequest request){
        Page<User> userPage = userRepository.findByUsernameContaining(request.getUsername(), PageRequest.of(request.getCurrentPage(), request.getSize()));
        List<UserResponse> users = userPage.getContent().stream().map(user -> {
            return UserResponse.builder()
                        .name(user.getName())
                        .username(user.getUsername())
                        .alamat(user.getAlamat())
                        .email(user.getEmail())
                        .photo(user.getPhoto())
                        .build();
        }).collect(Collectors.toList());

        PagingResponse pagingResponse = PagingResponse.builder()
                    .currentPage(userPage.getNumber())
                    .size(userPage.getSize()).totalPage(userPage.getTotalPages())
                    .build();

        return UserInquiryResponse.builder()
                .users(users)
                .paging(pagingResponse)
                .build();

    }

}
