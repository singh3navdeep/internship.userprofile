package in.dailyhunt.internship.userprofile.endpoints.bypassed;

import in.dailyhunt.internship.userprofile.client_model.response.Article;
import in.dailyhunt.internship.userprofile.services.interfaces.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(GenericArticleEndpoint.BASE_URL)
public class GenericArticleEndpoint {

    static final String BASE_URL = "api/v1/injestion/article";

    private final NewsService newsService;

    @Autowired
    public GenericArticleEndpoint(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/{injestionId}")
    ResponseEntity<Article> getNews(@PathVariable Long injestionId) {
        return ResponseEntity.ok().body(newsService.getNews(injestionId));
    }

}
