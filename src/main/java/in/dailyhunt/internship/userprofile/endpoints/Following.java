package in.dailyhunt.internship.userprofile.endpoints;

import in.dailyhunt.internship.userprofile.client_model.request.TopicOb;
import in.dailyhunt.internship.userprofile.services.interfaces.FollowingService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@NoArgsConstructor
@RestController
@RequestMapping(Following.BASE_URL)
public class Following {
    static final String BASE_URL = "api/v1/user_profile/following";

    @Autowired
    private FollowingService followingService;

    public Following(FollowingService followingService){
        this.followingService = followingService;
    }

    @PostMapping()
    public ResponseEntity<String> addFollowing(@Valid @RequestBody TopicOb topicob){
        followingService.addFollowing(topicob);
        return ResponseEntity.ok().body("topic added to following");
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteFollowing(@Valid @RequestBody TopicOb topicob){
        followingService.deleteFollowing(topicob);
        return ResponseEntity.ok().body("topic removed from following");
    }
}
