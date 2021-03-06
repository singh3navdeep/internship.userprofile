package in.dailyhunt.internship.userprofile.endpoints;

import in.dailyhunt.internship.userprofile.client_model.request.NewsComponentsRequest;
import in.dailyhunt.internship.userprofile.client_model.request.SourceRequest;
import in.dailyhunt.internship.userprofile.client_model.response.*;
import in.dailyhunt.internship.userprofile.entities.GenreData;
import in.dailyhunt.internship.userprofile.entities.LanguageData;
import in.dailyhunt.internship.userprofile.entities.LocalityData;
import in.dailyhunt.internship.userprofile.entities.TagData;
import in.dailyhunt.internship.userprofile.services.interfaces.NewsComponentsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(NewsComponentsEndpoint.BASE_URL)
public class NewsComponentsEndpoint {
    static final String BASE_URL = "api/v1/injestion/user_profile/newsComponents";

 //   private final WebClient.Builder webclientBuilder;

    private final NewsComponentsService newsComponentsService;

    @Autowired
    public NewsComponentsEndpoint(NewsComponentsService newsComponentsService) {
        this.newsComponentsService = newsComponentsService;
    }

    @GetMapping("/generic")
    ResponseEntity<AllGenres> getGenericGenres() {
        return ResponseEntity.ok().body(newsComponentsService.getAllGenericGenres());
    }

    @GetMapping("/genres")
    ResponseEntity<AllGenres> getAllGenres() {

        return ResponseEntity.ok().body(newsComponentsService.getAllGenres());
    }

    @PostMapping("/genre")
    public ResponseEntity<String> addGenre(@RequestBody NewsComponentsRequest newsComponentsRequest) {
        newsComponentsService.addGenre(newsComponentsRequest);
        return ResponseEntity.ok().body("Genre has been added successfully");
    }

    @DeleteMapping("/genre/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable Long id) {
        newsComponentsService.deleteGenre(id);
        return ResponseEntity.ok().body("Genre has been deleted successfully");
    }

    @PutMapping("/genre/{id}")
    public ResponseEntity<String> updateGeneric(@PathVariable Long id) {
        newsComponentsService.updateGeneric(id);
        return ResponseEntity.ok().body("Genre generic updated successfully");
    }

    @GetMapping("/languages")
    ResponseEntity<AllLanguages> getAllLanguages() {
        return ResponseEntity.ok().body(newsComponentsService.getAllLanguages());
    }

    @PostMapping("/language")
    public ResponseEntity<String> addLanguage(@Valid @RequestBody NewsComponentsRequest newsComponentsRequest) {
        newsComponentsService.addLanguage(newsComponentsRequest);
        return ResponseEntity.ok().body("Language has been added successfully");
    }
    @DeleteMapping("/language/{id}")
    public ResponseEntity<String> deleteLanguage(@PathVariable Long id) {
        newsComponentsService.deleteLanguage(id);
        return ResponseEntity.ok().body("Language has been deleted successfully");
    }

    @GetMapping("/localities")
    ResponseEntity<AllLocalities> getAllLocalities() {
        return ResponseEntity.ok().body(newsComponentsService.getAllLocalities());
    }

    @PostMapping("/locality")
    public ResponseEntity<String> addLocality(@Valid @RequestBody NewsComponentsRequest newsComponentsRequest) {
        newsComponentsService.addLocality(newsComponentsRequest);
        return ResponseEntity.ok().body("Locality has been added successfully");
    }
    @DeleteMapping("/locality/{id}")
    public ResponseEntity<String> deleteLocality(@PathVariable Long id) {
        newsComponentsService.deleteLocality(id);
        return ResponseEntity.ok().body("Locality has been deleted successfully");
    }

    @GetMapping("/tags")
    ResponseEntity<AllTags> getAllTags() {

        return ResponseEntity.ok().body(newsComponentsService.getAllTags());
    }

    @PostMapping("/tag")
    public ResponseEntity<String> addTag(@Valid @RequestBody NewsComponentsRequest newsComponentsRequest) {
        newsComponentsService.addTag(newsComponentsRequest);
        return ResponseEntity.ok().body("Tag has been added successfully");
    }
    @DeleteMapping("/tag/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable Long id) {
        newsComponentsService.deleteTag(id);
        return ResponseEntity.ok().body("Tag has been deleted successfully");
    }

    @GetMapping("/sources")
    ResponseEntity<AllSources> getAllSources() {
        return ResponseEntity.ok().body(newsComponentsService.getAllSources());
    }

    @PostMapping("/source")
    public ResponseEntity<String> addSource(@Valid @RequestBody SourceRequest sourceRequest) {
        newsComponentsService.addSource(sourceRequest);
        return ResponseEntity.ok().body("Source has been added successfully");
    }

    @DeleteMapping("/source/{id}")
    public ResponseEntity<String> deleteSource(@PathVariable Long id) {
        newsComponentsService.deleteSource(id);
        return ResponseEntity.ok().body("Source has been deleted successfully");
    }
}
