package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.client_model.response.*;
import in.dailyhunt.internship.userprofile.entities.BlockedSet;
import in.dailyhunt.internship.userprofile.entities.FollowingSet;
import in.dailyhunt.internship.userprofile.entities.User;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;
import in.dailyhunt.internship.userprofile.repositories.UserRepository;
import in.dailyhunt.internship.userprofile.services.interfaces.BlockedService;
import in.dailyhunt.internship.userprofile.services.interfaces.FollowingService;
import in.dailyhunt.internship.userprofile.services.interfaces.HomeService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class HomeServiceImpl implements HomeService {

    private final UserRepository userRepository;
    private final FollowingService followingService;
    private final BlockedService blockedService;
    private final WebClient.Builder webClientBuilder;

    public HomeServiceImpl(UserRepository userRepository, FollowingService followingService,
                           BlockedService blockedService, WebClient.Builder webClientBuilder){
        this.userRepository = userRepository;
        this.followingService = followingService;
        this.blockedService = blockedService;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    @Transactional
    public UserDetails getDetailsById(Long userid) {
        Optional<User> optional = userRepository.findById(userid);
        if(!optional.isPresent()){
            throw new ResourceNotFoundException("This user does not exist");
        }
        User user = optional.get();
        return UserDetails.from(user);

    }

    @Override
    @Transactional
    public PreferenceResponse getFollowingById(Long userid){
        Optional<User> optional = userRepository.findById(userid);
        if(!optional.isPresent()){
            throw new ResourceNotFoundException("This user does not exist");
        }
        Optional<FollowingSet> optional_following = followingService.getFollowingSet(userid);
        if(!optional_following.isPresent()){
            return null;
        }
        else{
            FollowingSet followingSet = optional_following.get();
            List<Long> genreIds = followingSet.getGenreIds();
    //        System.out.println("GENREID SIZE: "+genreIds.size());
    //        System.out.println("GenreId 1: "+genreIds.get(0));
    //        System.out.println("GenreId 2: "+genreIds.get(1));
            List<Long> languageIds = followingSet.getLanguageIds();
            List<Long> localityIds = followingSet.getLocalityIds();
            List<Long> tagIds = followingSet.getTagIds();

            String genreUrl = "http://localhost:8081/api/v1/genre/genreIds";
            AllGenres allGenres = webClientBuilder.build()
                    .post()
                    .uri(genreUrl)
                    .body(Mono.just(GenreIdList.builder().genreIds(genreIds).build()), GenreIdList.class)
                    .retrieve()
                    .bodyToMono(AllGenres.class)
                    .block();
            String languageUrl = "http://localhost:8081/api/v1/language/languageIds";
            AllLanguages allLanguages = webClientBuilder.build()
                    .post()
                    .uri(languageUrl)
                    .body(Mono.just(LanguageIdList.builder().languageIds(languageIds).build()), LanguageIdList.class)
                    .retrieve()
                    .bodyToMono(AllLanguages.class)
                    .block();
            String localityUrl = "http://localhost:8081/api/v1/locality/localityIds";
            AllLocalities allLocalities = webClientBuilder.build()
                    .post()
                    .uri(localityUrl)
                    .body(Mono.just(LocalityIdList.builder().localityIds(localityIds).build()), LocalityIdList.class)
                    .retrieve()
                    .bodyToMono(AllLocalities.class)
                    .block();
            String tagUrl = "http://localhost:8081/api/v1/tag/tagIds";
            AllTags allTags = webClientBuilder.build()
                    .post()
                    .uri(tagUrl)
                    .body(Mono.just(TagIdList.builder().tagIds(tagIds).build()), TagIdList.class)
                    .retrieve()
                    .bodyToMono(AllTags.class)
                    .block();
            return PreferenceResponse.builder()
                    .genres(allGenres)
                    .languages(allLanguages)
                    .localities(allLocalities)
                    .tags(allTags)
                    .build();
        }
    }

    @Override
    @Transactional
    public PreferenceResponse getBlockedById(Long userid){

        Optional<User> optional = userRepository.findById(userid);
        if(!optional.isPresent()){
            throw new ResourceNotFoundException("This user does not exist");
        }

        Optional<BlockedSet> optional_blocked = blockedService.getBlockedSet(userid);
        if(!optional_blocked.isPresent()){
            return null;
        }
        else{
            BlockedSet blocked = optional_blocked.get();
            List<Long> genreIds = blocked.getGenreIds();
            List<Long> languageIds = blocked.getLanguageIds();
            List<Long> localityIds = blocked.getLocalityIds();
            List<Long> tagIds = blocked.getTagIds();

            String genreUrl = "http://localhost:8081/api/v1/genre/genreIds";
            AllGenres allGenres = webClientBuilder.build()
                    .post()
                    .uri(genreUrl)
                    .body(Mono.just(GenreIdList.builder().genreIds(genreIds).build()), GenreIdList.class)
                    .retrieve()
                    .bodyToMono(AllGenres.class)
                    .block();
            String languageUrl = "http://localhost:8081/api/v1/language/languageIds";
            AllLanguages allLanguages = webClientBuilder.build()
                    .post()
                    .uri(languageUrl)
                    .body(Mono.just(LanguageIdList.builder().languageIds(languageIds).build()), LanguageIdList.class)
                    .retrieve()
                    .bodyToMono(AllLanguages.class)
                    .block();
            String localityUrl = "http://localhost:8081/api/v1/locality/localityIds";
            AllLocalities allLocalities = webClientBuilder.build()
                    .post()
                    .uri(localityUrl)
                    .body(Mono.just(LocalityIdList.builder().localityIds(localityIds).build()), LocalityIdList.class)
                    .retrieve()
                    .bodyToMono(AllLocalities.class)
                    .block();
            String tagUrl = "http://localhost:8081/api/v1/tag/tagIds";
            AllTags allTags = webClientBuilder.build()
                    .post()
                    .uri(tagUrl)
                    .body(Mono.just(TagIdList.builder().tagIds(tagIds).build()), TagIdList.class)
                    .retrieve()
                    .bodyToMono(AllTags.class)
                    .block();
            return PreferenceResponse.builder()
                    .genres(allGenres)
                    .languages(allLanguages)
                    .localities(allLocalities)
                    .tags(allTags)
                    .build();
        }
    }
}
