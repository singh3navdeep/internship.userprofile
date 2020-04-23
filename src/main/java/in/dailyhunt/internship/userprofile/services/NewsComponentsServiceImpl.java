package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.client_model.response.AllGenres;
import in.dailyhunt.internship.userprofile.client_model.response.AllLanguages;
import in.dailyhunt.internship.userprofile.client_model.response.AllLocalities;
import in.dailyhunt.internship.userprofile.client_model.response.AllTags;
import in.dailyhunt.internship.userprofile.services.interfaces.NewsComponentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NewsComponentsServiceImpl implements NewsComponentsService {

    @LoadBalanced
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public NewsComponentsServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public AllGenres getAllGenres() {
        String genreUrl = "http://localhost:8081/api/v1/genre";
        return webClientBuilder.build()
                .get()
                .uri(genreUrl)
                .retrieve()
                .bodyToMono(AllGenres.class)
                .block();
    }


    @Override
    public AllLanguages getAllLanguages() {
        String languageUrl = "http://localhost:8081/api/v1/language";
        return webClientBuilder.build()
                .get()
                .uri(languageUrl)
                .retrieve()
                .bodyToMono(AllLanguages.class)
                .block();
    }

    @Override
    public AllLocalities getAllLocalities() {
        String localityUrl = "http://localhost:8081/api/v1/locality";
        return webClientBuilder.build()
                .get()
                .uri(localityUrl)
                .retrieve()
                .bodyToMono(AllLocalities.class)
                .block();
    }

    @Override
    public AllTags getAllTags() {
        String tagUrl = "http://localhost:8081/api/v1/tag";
        return webClientBuilder.build()
                .get()
                .uri(tagUrl)
                .retrieve()
                .bodyToMono(AllTags.class)
                .block();
    }
}
