package in.dailyhunt.internship.userprofile.endpoints;

import in.dailyhunt.internship.userprofile.client_model.response.NavigationResponse;
import in.dailyhunt.internship.userprofile.endpoints.profile.FollowingEndpoint;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;
import in.dailyhunt.internship.userprofile.services.interfaces.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(NavigationEndpoint.BASE_URL)
public class NavigationEndpoint {
    static final String BASE_URL = "api/v1/user_profile/navigation";

    private final NavigationService navigationService;

    @Autowired
    public NavigationEndpoint(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    @GetMapping
    public ResponseEntity<NavigationResponse> getNavigation() {
        return ResponseEntity.ok().body(navigationService.getNavigation());
    }

    @PostMapping("/genre/{genreId}")
    public ResponseEntity<String> addGenre(@Valid @PathVariable Long genreId) throws
            ResourceNotFoundException {
        navigationService.addGenre(genreId);
        return ResponseEntity.ok().body("genre added to navigation");
    }

    @PostMapping("/locality/{localityId}")
    public ResponseEntity<String> addLocality(@Valid @PathVariable Long localityId) throws
            ResourceNotFoundException {
        navigationService.addLocality(localityId);
        return ResponseEntity.ok().body("locality added to navigation");
    }

    @PostMapping("/tag/{tagId}")
    public ResponseEntity<String> addTag(@Valid @PathVariable Long tagId) throws
            ResourceNotFoundException {
        navigationService.addTag(tagId);
        return ResponseEntity.ok().body("tag added to navigation");
    }

    @PostMapping("source/{sourceId}")
    public ResponseEntity<String> addSource(@Valid @PathVariable Long sourceId) throws
            ResourceNotFoundException {
        navigationService.addSource(sourceId);
        return ResponseEntity.ok().body("source added to navigation");
    }
    @DeleteMapping("/genre/{genreId}")
    public ResponseEntity<String> deleteGenre(@Valid @PathVariable Long genreId) throws
            ResourceNotFoundException {
        navigationService.deleteGenre(genreId);
        return ResponseEntity.ok().body("genre deleted from navigation");
    }

    @DeleteMapping("/locality/{localityId}")
    public ResponseEntity<String> deleteLocality(@Valid @PathVariable Long localityId) throws
            ResourceNotFoundException{
        navigationService.deleteLocality(localityId);
        return ResponseEntity.ok().body("locality deleted from navigation");
    }

    @DeleteMapping("/tag/{tagId}")
    public ResponseEntity<String> deleteTag(@Valid @PathVariable Long tagId) throws
            ResourceNotFoundException {
        navigationService.deleteTag(tagId);
        return ResponseEntity.ok().body("tag delete from navigation");
    }

    @DeleteMapping("source/{sourceId}")
    public ResponseEntity<String> deleteSource(@Valid @PathVariable Long sourceId) throws
            ResourceNotFoundException {
        navigationService.deleteSource(sourceId);
        return ResponseEntity.ok().body("source deleted from navigation");
    }
}
