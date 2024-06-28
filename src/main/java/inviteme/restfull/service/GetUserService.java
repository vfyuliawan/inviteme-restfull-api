package inviteme.restfull.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import inviteme.restfull.entiity.User;
import inviteme.restfull.repository.UserRepository;

@Service
public class GetUserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUserLogin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            User userDetails = (User) principal;
            // User user = new User();
            // user.setUsername(userDetails.getUsername());
            // user.setName(userDetails.getName());
            // user.setProjects(userDetails.getProjects());
            User userResponse = userRepository.findUserByToken(userDetails.getToken())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbiden Kingdom"));
            return userResponse;
        } else {
            throw new RuntimeException("User details not found");
        }

    }

}
