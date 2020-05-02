package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.client_model.response.CardNews;
import in.dailyhunt.internship.userprofile.services.interfaces.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class NewsServiceImpl implements NewsService {

    private WebClient.Builder webClientBuilder;

    @Autowired
    public NewsServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public CardNews getNews(Long id) {
        String newsUrl = "https://dailyhunt-injestion-service.herokuapp.com/api/v1/auth/news";

        return webClientBuilder.build()
                .get()
                .uri(newsUrl+"/"+id)
                .retrieve()
                .bodyToMono(CardNews.class)
                .block();
    }
}
