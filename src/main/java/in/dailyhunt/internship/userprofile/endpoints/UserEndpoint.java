package in.dailyhunt.internship.userprofile.endpoints;

import in.dailyhunt.internship.userprofile.client_model.request.LoginForm;
import in.dailyhunt.internship.userprofile.client_model.request.SignUpForm;
import in.dailyhunt.internship.userprofile.client_model.response.JwtResponse;
import in.dailyhunt.internship.userprofile.entities.User;
import in.dailyhunt.internship.userprofile.exceptions.BadRequestException;
import in.dailyhunt.internship.userprofile.security.jwt.JwtProvider;
import in.dailyhunt.internship.userprofile.services.interfaces.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(UserEndpoint.BASE_URL)
public class UserEndpoint {

    static final String BASE_URL = "api/v1/user_profile/auth";


    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserEndpoint(UserService userService, AuthenticationManager authenticationManager,
                        JwtProvider jwtProvider){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
       }

/*    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }
*/
    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@Valid @RequestBody SignUpForm signUpRequest) throws BadRequestException {
        userService.saveUser(signUpRequest);
        return ResponseEntity.ok().body("User created successfully!");
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        System.out.println("reached here first");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        System.out.println("reached after authentication:");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }




/*    @GetMapping("{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.findUserById(userId));
    }
*/

}
