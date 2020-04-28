package in.dailyhunt.internship.userprofile.endpoints;
import in.dailyhunt.internship.userprofile.client_model.request.PreferenceRequest;
import in.dailyhunt.internship.userprofile.services.interfaces.BlockedService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(BlockedEndpoint.BASE_URL)
public class BlockedEndpoint {
    static final String BASE_URL = "api/v1/user_profile/blocked";

    private final BlockedService blockedService;

    @Autowired
    public BlockedEndpoint(BlockedService blockedService){
        this.blockedService = blockedService;
    }

    @PostMapping()
    public ResponseEntity<String> addBlocked(@Valid @RequestBody PreferenceRequest request){
        blockedService.addBlocked(request);
        return ResponseEntity.ok().body("topic added to Blocked");
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteBlocked(@Valid @RequestBody PreferenceRequest request){
        blockedService.deleteBlocked(request);
        return ResponseEntity.ok().body("topic removed from Blocked");
    }
}
