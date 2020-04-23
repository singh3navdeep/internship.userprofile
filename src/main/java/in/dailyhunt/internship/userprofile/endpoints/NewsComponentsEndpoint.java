package in.dailyhunt.internship.userprofile.endpoints;

import in.dailyhunt.internship.userprofile.client_model.response.AllGenres;
import in.dailyhunt.internship.userprofile.client_model.response.AllLanguages;
import in.dailyhunt.internship.userprofile.client_model.response.AllLocalities;
import in.dailyhunt.internship.userprofile.client_model.response.AllTags;
import in.dailyhunt.internship.userprofile.services.interfaces.NewsComponentsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping(NewsComponentsEndpoint.BASE_URL)
public class NewsComponentsEndpoint {
    static final String BASE_URL = "api/v1/user_profile/newsComponents";

    private final WebClient.Builder webclientBuilder;

    private final NewsComponentsService newsComponentsService;

    @Autowired
    public NewsComponentsEndpoint(WebClient.Builder webclientBuilder, NewsComponentsService newsComponentsService) {
        this.webclientBuilder = webclientBuilder;
        this.newsComponentsService = newsComponentsService;
    }

    @GetMapping("/genre")
    ResponseEntity<AllGenres> getAllGenres() {

        return ResponseEntity.ok().body(newsComponentsService.getAllGenres());
    }

    @GetMapping("/language")
    ResponseEntity<AllLanguages> getAllLanguages() {
        return ResponseEntity.ok().body(newsComponentsService.getAllLanguages());
    }

    @GetMapping("/locality")
    ResponseEntity<AllLocalities> getAllLocalities() {
        return ResponseEntity.ok().body(newsComponentsService.getAllLocalities());
    }

    @GetMapping("/tag")
    ResponseEntity<AllTags> getAllTags() {
        return ResponseEntity.ok().body(newsComponentsService.getAllTags());
    }

}
