package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.client_model.request.NewsComponentsRequest;
import in.dailyhunt.internship.userprofile.client_model.response.AllGenres;
import in.dailyhunt.internship.userprofile.client_model.response.AllLanguages;
import in.dailyhunt.internship.userprofile.client_model.response.AllLocalities;
import in.dailyhunt.internship.userprofile.client_model.response.AllTags;
import in.dailyhunt.internship.userprofile.entities.GenreData;
import in.dailyhunt.internship.userprofile.entities.LanguageData;
import in.dailyhunt.internship.userprofile.entities.LocalityData;
import in.dailyhunt.internship.userprofile.entities.TagData;
import in.dailyhunt.internship.userprofile.repositories.GenreDataRepository;
import in.dailyhunt.internship.userprofile.repositories.LanguageDataRepository;
import in.dailyhunt.internship.userprofile.repositories.LocalityDataRepository;
import in.dailyhunt.internship.userprofile.repositories.TagDataRepository;
import in.dailyhunt.internship.userprofile.services.interfaces.NewsComponentsService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class NewsComponentsServiceImpl implements NewsComponentsService {

    /*@LoadBalanced
    private final WebClient.Builder webClientBuilder;
*/
    private final GenreDataRepository genreDataRepository;
    private final LanguageDataRepository languageDataRepository;
    private final LocalityDataRepository localityDataRepository;
    private final TagDataRepository tagDataRepository;
    @Autowired
    public NewsComponentsServiceImpl(GenreDataRepository genreDataRepository,
                                     LanguageDataRepository languageDataRepository,
                                     LocalityDataRepository localityDataRepository,
                                     TagDataRepository tagDataRepository) {
        this.genreDataRepository = genreDataRepository;
        this.languageDataRepository = languageDataRepository;
        this.localityDataRepository = localityDataRepository;
        this.tagDataRepository = tagDataRepository;
    }

    @Override
    public AllGenres getAllGenres() {
    /*    String genreUrl = "http://localhost:8081/api/v1/genre";
        return webClientBuilder.build()
                .get()
                .uri(genreUrl)
                .retrieve()
                .bodyToMono(AllGenres.class)
                .block();
    */
        return AllGenres.builder()
                .all_the_genres(genreDataRepository.findAll())
                .build();
    }

    @Override
    public void addGenre(NewsComponentsRequest newsComponentsRequest) {
        genreDataRepository.save(GenreData.builder()
                .injestionId(newsComponentsRequest.getId())
                .name(newsComponentsRequest.getName())
                .build());
    }

    public void deleteGenre(Long id) {
        genreDataRepository.delete(genreDataRepository.findByInjestionId(id));
    }
    @Override
    public AllLanguages getAllLanguages() {
    /*    String languageUrl = "http://localhost:8081/api/v1/language";
        return webClientBuilder.build()
                .get()
                .uri(languageUrl)
                .retrieve()
                .bodyToMono(AllLanguages.class)
                .block();
    */
        return AllLanguages.builder()
                .all_the_languages(languageDataRepository.findAll())
                .build();
    }

    @Override
    public void addLanguage(NewsComponentsRequest newsComponentsRequest) {
        languageDataRepository.save(LanguageData.builder()
                .injestionId(newsComponentsRequest.getId())
                .name(newsComponentsRequest.getName())
                .build());
    }
    public void deleteLanguage(Long id) {
        languageDataRepository.delete(languageDataRepository.findByInjestionId(id));
    }

    @Override
    public AllLocalities getAllLocalities() {
    /*      String localityUrl = "http://localhost:8081/api/v1/locality";
        return webClientBuilder.build()
                .get()
                .uri(localityUrl)
                .retrieve()
                .bodyToMono(AllLocalities.class)
                .block();
    */
        return AllLocalities.builder()
                .all_the_localities(localityDataRepository.findAll())
                .build();
    }

    @Override
    public void addLocality(NewsComponentsRequest newsComponentsRequest) {
        localityDataRepository.save(LocalityData.builder()
                .injestionId(newsComponentsRequest.getId())
                .name(newsComponentsRequest.getName())
                .build());
    }
    public void deleteLocality(Long id) {
        localityDataRepository.delete(localityDataRepository.findByInjestionId(id));
    }

    @Override
    public AllTags getAllTags() {
    /*        String tagUrl = "http://localhost:8081/api/v1/tag";
        return webClientBuilder.build()
                .get()
                .uri(tagUrl)
                .retrieve()
                .bodyToMono(AllTags.class)
                .block();
    */
        return AllTags.builder()
                .all_the_tags(tagDataRepository.findAll())
                .build();
    }

    @Override
    public void addTag(NewsComponentsRequest newsComponentsRequest) {
        tagDataRepository.save(TagData.builder()
                .injestionId(newsComponentsRequest.getId())
                .name(newsComponentsRequest.getName())
                .build());
    }
    public void deleteTag(Long id) {
        tagDataRepository.delete(tagDataRepository.findByInjestionId(id));
    }

}
