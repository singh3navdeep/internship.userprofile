package in.dailyhunt.internship.userprofile.endpoints.profile;

import in.dailyhunt.internship.userprofile.client_model.response.PreferenceResponse;
import in.dailyhunt.internship.userprofile.services.interfaces.HomeService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(HomeEndpoint.HOME_BASE_URL)
public class HomeEndpoint {
    static final String HOME_BASE_URL = "api/v1/home_profile";

    private final HomeService homeService;

    @Autowired
    public HomeEndpoint(HomeService homeService){
        this.homeService = homeService;
    }


    //home page of user profile

    @GetMapping("/{userId}")
    public ResponseEntity<?> getDetails(@PathVariable Long userId){
        return ResponseEntity.ok().body(homeService.getDetailsById(userId));
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<PreferenceResponse> getFollowingList(@PathVariable Long userId){
        return ResponseEntity.ok().body(homeService.getFollowingById(userId));
    }

    @GetMapping("/blocked/{userId}")
    public ResponseEntity<PreferenceResponse> getBlockedList(@PathVariable Long userId){
        return ResponseEntity.ok().body(homeService.getBlockedById(userId));
    }

}
