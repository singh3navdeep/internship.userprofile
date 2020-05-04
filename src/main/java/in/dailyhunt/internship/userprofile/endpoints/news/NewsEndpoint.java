package in.dailyhunt.internship.userprofile.endpoints.news;

import in.dailyhunt.internship.userprofile.client_model.response.Article;
import in.dailyhunt.internship.userprofile.services.interfaces.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(NewsEndpoint.BASE_URL)
public class NewsEndpoint {

    static final String BASE_URL = "api/v1/news";

    private final NewsService newsService;

    @Autowired
    public NewsEndpoint(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/{injestionId}")
    ResponseEntity<Article> getNews(@PathVariable Long injestionId) {
        return ResponseEntity.ok().body(newsService.getNews(injestionId));
    }
}
