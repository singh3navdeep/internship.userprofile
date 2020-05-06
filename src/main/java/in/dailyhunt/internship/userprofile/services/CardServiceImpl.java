package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.client_model.request.*;
import in.dailyhunt.internship.userprofile.client_model.response.Card;
import in.dailyhunt.internship.userprofile.client_model.response.CardResponse;
import in.dailyhunt.internship.userprofile.entities.*;
import in.dailyhunt.internship.userprofile.exceptions.BadRequestException;
import in.dailyhunt.internship.userprofile.repositories.GenreDataRepository;
import in.dailyhunt.internship.userprofile.security.services.UserPrinciple;
import in.dailyhunt.internship.userprofile.services.interfaces.BlockedService;
import in.dailyhunt.internship.userprofile.services.interfaces.CardService;
import in.dailyhunt.internship.userprofile.services.interfaces.FollowingService;
import in.dailyhunt.internship.userprofile.services.interfaces.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.Security;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final HomeService homeService;
    private final FollowingService followingService;
    private final BlockedService blockedService;
    private final WebClient.Builder webClientBuilder;
    private final GenreDataRepository genreDataRepository;

    @Autowired
    public CardServiceImpl(HomeService homeService, FollowingService followingService,
                           BlockedService blockedService, WebClient.Builder webClientBuilder,
                           GenreDataRepository genreDataRepository) {
        this.homeService = homeService;
        this.followingService = followingService;
        this.blockedService = blockedService;
        this.webClientBuilder = webClientBuilder;
        this.genreDataRepository = genreDataRepository;
    }

    @Override
    public CardResponse getKeywordCards(String keyword) {

        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/keyword";

        return  webClientBuilder.build()
                .get()
                .uri(recoUrl+"/"+keyword)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();

    }

    @Override
    public CardResponse getGenericCards() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        FilterGenreIds filterGenreIds = FilterGenreIds.builder()
                        .genreIds(genreDataRepository.findByGenericTrue()
                                    .stream()
                                    .map(GenreData::getInjestionId)
                                    .collect(Collectors.toSet()))
                        .build();
        Optional<BlockedSet> optionalBlocked = blockedService.getBlockedSet(userId);
        if(optionalBlocked.isPresent()) {
            filterGenreIds.getGenreIds().removeAll(homeService.getBlockedById(userId)
                    .getGenres().getAll_the_genres()
                    .stream().map(GenreData::getInjestionId)
                    .collect(Collectors.toSet()));
        }

        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/genreIds";

        return  webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterGenreIds), FilterGenreIds.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
    }

    @Override
    public CardResponse getGenreCards(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<FollowingSet> optional_following = followingService.getFollowingSet(userId);
        if(!optional_following.isPresent()){
            return CardResponse.builder()
                    .cards(new HashSet<>())
                    .build();
        }
        FilterGenreIds filterGenreIds = FilterGenreIds.builder()
                        .genreIds(homeService.getFollowingById(userId)
                        .getGenres().getAll_the_genres()
                        .stream().map(GenreData::getInjestionId)
                        .collect(Collectors.toSet()))
                        .build();
    /*    Optional<BlockedSet> optionalBlocked = blockedService.getBlockedSet(userId);
        if(optionalBlocked.isPresent()) {
            filterGenreIds.getGenreIds().removeAll(homeService.getBlockedById(userId)
                    .getGenres().getAll_the_genres()
                    .stream().map(GenreData::getInjestionId)
                    .collect(Collectors.toSet()));
        }
    */
        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/genreIds";

        return  webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterGenreIds), FilterGenreIds.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();

    }

    @Override
    public CardResponse getLanguageCards() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<FollowingSet> optional_following = followingService.getFollowingSet(userId);
        if(!optional_following.isPresent()){
            return CardResponse.builder()
                    .cards(new HashSet<>())
                    .build();
        }
        FilterLanguageIds filterLanguageIds = FilterLanguageIds.builder()
                .languageIds(homeService.getFollowingById(userId)
                        .getLanguages().getAll_the_languages()
                        .stream().map(LanguageData::getInjestionId)
                        .collect(Collectors.toSet()))
                .build();
    /*    Optional<BlockedSet> optionalBlocked = blockedService.getBlockedSet(userId);
        if(optionalBlocked.isPresent()) {
            filterGenreIds.getGenreIds().removeAll(homeService.getBlockedById(userId)
                    .getGenres().getAll_the_genres()
                    .stream().map(GenreData::getInjestionId)
                    .collect(Collectors.toSet()));
        }
    */
        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/languageIds";

        return  webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterLanguageIds), FilterLanguageIds.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();

    }

    @Override
    public CardResponse getLocalityCards() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<FollowingSet> optional_following = followingService.getFollowingSet(userId);
        if(!optional_following.isPresent()){
            return CardResponse.builder()
                    .cards(new HashSet<>())
                    .build();
        }
        FilterLocalityIds filterLocalityIds = FilterLocalityIds.builder()
                .localityIds(homeService.getFollowingById(userId)
                        .getLocalities().getAll_the_localities()
                        .stream().map(LocalityData::getInjestionId)
                        .collect(Collectors.toSet()))
                .build();
    /*    Optional<BlockedSet> optionalBlocked = blockedService.getBlockedSet(userId);
        if(optionalBlocked.isPresent()) {
            filterGenreIds.getGenreIds().removeAll(homeService.getBlockedById(userId)
                    .getGenres().getAll_the_genres()
                    .stream().map(GenreData::getInjestionId)
                    .collect(Collectors.toSet()));
        }
    */
        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/localityIds";

        return  webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterLocalityIds), FilterLocalityIds.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();

    }

    @Override
    public CardResponse getTagCards() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<FollowingSet> optional_following = followingService.getFollowingSet(userId);
        if(!optional_following.isPresent()){
            return CardResponse.builder()
                    .cards(new HashSet<>())
                    .build();
        }
        FilterTagIds filterTagIds = FilterTagIds.builder()
                .tagIds(homeService.getFollowingById(userId)
                        .getTags().getAll_the_tags()
                        .stream().map(TagData::getInjestionId)
                        .collect(Collectors.toSet()))
                .build();
    /*    Optional<BlockedSet> optionalBlocked = blockedService.getBlockedSet(userId);
        if(optionalBlocked.isPresent()) {
            filterGenreIds.getGenreIds().removeAll(homeService.getBlockedById(userId)
                    .getGenres().getAll_the_genres()
                    .stream().map(GenreData::getInjestionId)
                    .collect(Collectors.toSet()));
        }
    */
        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/tagIds";

        return  webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterTagIds), FilterTagIds.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();

    }

    @Override
    public CardResponse getTrendingCards() {

        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/trending";

        return  webClientBuilder.build()
                .get()
                .uri(recoUrl)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();

    }

    @Override
    public CardResponse getCardsByDateRange(DateFilter dateFilter) {
        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/date-range";

        return  webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(dateFilter), DateFilter.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();

    }
}
