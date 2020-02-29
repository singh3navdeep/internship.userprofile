package in.dailyhunt.internship.userprofile.endpoints;
import in.dailyhunt.internship.userprofile.client_model.request.TopicOb;
import in.dailyhunt.internship.userprofile.services.interfaces.BlockedService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
@NoArgsConstructor
@RestController
@RequestMapping(Blocked.BASE_URL)
public class Blocked {
    static final String BASE_URL = "api/v1/user_profile/blocked";
    @Autowired
    private BlockedService blockedService;
    public Blocked(BlockedService blockedService){
        this.blockedService = blockedService;
    }
    @PostMapping()
    public ResponseEntity<String> addBlocked(@Valid @RequestBody TopicOb topicob){
        blockedService.addBlocked(topicob);
        return ResponseEntity.ok().body("topic added to Blocked");
    }
    @DeleteMapping()
    public ResponseEntity<String> deleteBlocked(@Valid @RequestBody TopicOb topicob){
        blockedService.deleteBlocked(topicob);
        return ResponseEntity.ok().body("topic removed from Blocked");
    }
}
