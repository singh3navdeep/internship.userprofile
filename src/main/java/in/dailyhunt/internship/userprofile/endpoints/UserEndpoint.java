package in.dailyhunt.internship.userprofile.endpoints;

import in.dailyhunt.internship.userprofile.client_model.response.UserResponse;
import in.dailyhunt.internship.userprofile.security.services.UserPrinciple;
import in.dailyhunt.internship.userprofile.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(UserEndpoint.BASE_URL)
public class UserEndpoint {

    static final String BASE_URL = "api/v1/user_profile";

    private final UserService userService;

    @Autowired
    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public ResponseEntity<?> user() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        return ResponseEntity.ok().body(UserResponse.from(userService.findUserById(user.getId())));
    }
}
