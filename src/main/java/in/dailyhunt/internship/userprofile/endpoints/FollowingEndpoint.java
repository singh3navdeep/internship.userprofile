package in.dailyhunt.internship.userprofile.endpoints;

import in.dailyhunt.internship.userprofile.client_model.request.PreferenceRequest;
import in.dailyhunt.internship.userprofile.services.interfaces.FollowingService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@NoArgsConstructor
@RestController
@RequestMapping(FollowingEndpoint.BASE_URL)
public class FollowingEndpoint {
    static final String BASE_URL = "api/v1/user_profile/following";


    private FollowingService followingService;

    @Autowired
    public FollowingEndpoint(FollowingService followingService){
        this.followingService = followingService;
    }

    @PostMapping()
    public ResponseEntity<String> addFollowing(@Valid @RequestBody PreferenceRequest request){
        followingService.addFollowing(request);
        return ResponseEntity.ok().body("preferences added to following");
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteFollowing(@Valid @RequestBody PreferenceRequest request){
        followingService.deleteFollowing(request);
        return ResponseEntity.ok().body("preferences removed from following");
    }
}
