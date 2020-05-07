package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.client_model.request.*;
import in.dailyhunt.internship.userprofile.client_model.response.Card;
import in.dailyhunt.internship.userprofile.client_model.response.CardResponse;
import in.dailyhunt.internship.userprofile.client_model.response.FilterSet;
import in.dailyhunt.internship.userprofile.entities.*;
import in.dailyhunt.internship.userprofile.exceptions.BadRequestException;
import in.dailyhunt.internship.userprofile.repositories.GenreDataRepository;
import in.dailyhunt.internship.userprofile.repositories.LanguageDataRepository;
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
    private final LanguageDataRepository languageDataRepository;

    @Autowired
    public CardServiceImpl(HomeService homeService, FollowingService followingService,
                           BlockedService blockedService, WebClient.Builder webClientBuilder,
                           GenreDataRepository genreDataRepository,
                           LanguageDataRepository languageDataRepository) {
        this.homeService = homeService;
        this.followingService = followingService;
        this.blockedService = blockedService;
        this.webClientBuilder = webClientBuilder;
        this.genreDataRepository = genreDataRepository;
        this.languageDataRepository = languageDataRepository;
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
        FilterSet filterSet = FilterSet.builder()
                .genreIds(filterGenreIds.getGenreIds())
                .build();
        Optional<FollowingSet> optional_following = followingService.getFollowingSet(userId);
        if(optional_following.isPresent()) {
            FilterLanguageIds filterLanguageIds = FilterLanguageIds.builder()
                    .languageIds(homeService.getFollowingById(userId)
                            .getLanguages().getAll_the_languages()
                            .stream()
                            .map(LanguageData::getInjestionId)
                            .collect(Collectors.toSet()))
                    .build();
            filterSet.setLanguageIds(filterLanguageIds.getLanguageIds());
        }
        else {
            FilterLanguageIds filterLanguageIds = FilterLanguageIds.builder()
                    .languageIds(languageDataRepository.findAll()
                    .stream()
                    .map(LanguageData::getInjestionId)
                    .collect(Collectors.toSet()))
                    .build();
            filterSet.setLanguageIds(filterLanguageIds.getLanguageIds());
        }
        /*Optional<BlockedSet> optionalBlocked = blockedService.getBlockedSet(userId);
        if(optionalBlocked.isPresent()) {
            filterGenreIds.getGenreIds().removeAll(homeService.getBlockedById(userId)
                    .getGenres().getAll_the_genres()
                    .stream().map(GenreData::getInjestionId)
                    .collect(Collectors.toSet()));    FilterLocalityIds filterLocalityIds = FilterLocalityIds.builder()
                    .localityIds(homeService.getBlockedById(userId)
                            .getLocalities().getAll_the_localities()
                            .stream()
                            .map(LocalityData::getInjestionId)
                            .collect(Collectors.toSet()))
                    .build();

            FilterTagIds filterTagIds = FilterTagIds.builder()
                    .tagIds(homeService.getBlockedById(userId)
                            .getTags().getAll_the_tags()
                            .stream()
                            .map(TagData::getInjestionId)
                            .collect(Collectors.toSet()))
                    .build();

            filterSet.setLocalityIds(filterLocalityIds.getLocalityIds());
            filterSet.setTagIds(filterTagIds.getTagIds());
        }
        else {
            filterSet.setLocalityIds(new HashSet<>());
            filterSet.setTagIds(new HashSet<>());
        }
*/

        filterSet.setLocalityIds(new HashSet<>());
        filterSet.setTagIds(new HashSet<>());
        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/genreIds";

        return  webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();

    }

    @Override
    public CardResponse getGenresCards(){
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

        FilterLanguageIds filterLanguageIds = FilterLanguageIds.builder()
                .languageIds(homeService.getFollowingById(userId)
                        .getLanguages().getAll_the_languages()
                        .stream()
                        .map(LanguageData::getInjestionId)
                        .collect(Collectors.toSet()))
                .build();

        FilterSet filterSet = FilterSet.builder()
                                .genreIds(filterGenreIds.getGenreIds())
                                .languageIds(filterLanguageIds.getLanguageIds())
                                .build();
        Optional<BlockedSet> optionalBlocked = blockedService.getBlockedSet(userId);
        if(optionalBlocked.isPresent()) {
            FilterLocalityIds filterLocalityIds = FilterLocalityIds.builder()
                    .localityIds(homeService.getBlockedById(userId)
                        .getLocalities().getAll_the_localities()
                        .stream()
                        .map(LocalityData::getInjestionId)
                        .collect(Collectors.toSet()))
                        .build();

            FilterTagIds filterTagIds = FilterTagIds.builder()
                    .tagIds(homeService.getBlockedById(userId)
                        .getTags().getAll_the_tags()
                        .stream()
                        .map(TagData::getInjestionId)
                        .collect(Collectors.toSet()))
                        .build();

            filterSet.setLocalityIds(filterLocalityIds.getLocalityIds());
            filterSet.setTagIds(filterTagIds.getTagIds());
        }
        else {
            filterSet.setLocalityIds(new HashSet<>());
            filterSet.setTagIds(new HashSet<>());
        }

        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/genreIds";

        return  webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();

    }

    @Override
    public CardResponse getGenreCards(Long genreId) {

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
                .genreIds(new HashSet<>(Collections.singleton(genreId)))
                .build();
        FilterLanguageIds filterLanguageIds = FilterLanguageIds.builder()
                .languageIds(homeService.getFollowingById(userId)
                        .getLanguages().getAll_the_languages()
                        .stream()
                        .map(LanguageData::getInjestionId)
                        .collect(Collectors.toSet()))
                .build();

        FilterSet filterSet = FilterSet.builder()
                .genreIds(filterGenreIds.getGenreIds())
                .languageIds(filterLanguageIds.getLanguageIds())
                .build();
        Optional<BlockedSet> optionalBlocked = blockedService.getBlockedSet(userId);
        if(optionalBlocked.isPresent()) {

            FilterLocalityIds filterLocalityIds = FilterLocalityIds.builder()
                    .localityIds(homeService.getBlockedById(userId)
                            .getLocalities().getAll_the_localities()
                            .stream()
                            .map(LocalityData::getInjestionId)
                            .collect(Collectors.toSet()))
                    .build();

            FilterTagIds filterTagIds = FilterTagIds.builder()
                    .tagIds(homeService.getBlockedById(userId)
                            .getTags().getAll_the_tags()
                            .stream()
                            .map(TagData::getInjestionId)
                            .collect(Collectors.toSet()))
                    .build();

            filterSet.setLocalityIds(filterLocalityIds.getLocalityIds());
            filterSet.setTagIds(filterTagIds.getTagIds());
        }
        else {
            filterSet.setLocalityIds(new HashSet<>());
            filterSet.setTagIds(new HashSet<>());
        }


        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/genreIds";

        return  webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
    }

    @Override
    public CardResponse getLanguagesCards() {
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

        FilterSet filterSet = FilterSet.builder()
                .languageIds(filterLanguageIds.getLanguageIds())
                .build();

        Optional<BlockedSet> optionalBlocked = blockedService.getBlockedSet(userId);
        if(optionalBlocked.isPresent()) {
            FilterGenreIds filterGenreIds = FilterGenreIds.builder()
                    .genreIds(homeService.getBlockedById(userId)
                    .getGenres().getAll_the_genres()
                    .stream().map(GenreData::getInjestionId)
                    .collect(Collectors.toSet()))
                    .build();
            FilterLocalityIds filterLocalityIds = FilterLocalityIds.builder()
                    .localityIds(homeService.getBlockedById(userId)
                            .getLocalities().getAll_the_localities()
                            .stream()
                            .map(LocalityData::getInjestionId)
                            .collect(Collectors.toSet()))
                    .build();
            FilterTagIds filterTagIds = FilterTagIds.builder()
                    .tagIds(homeService.getBlockedById(userId)
                            .getTags().getAll_the_tags()
                            .stream()
                            .map(TagData::getInjestionId)
                            .collect(Collectors.toSet()))
                    .build();
            filterSet.setGenreIds(filterGenreIds.getGenreIds());
            filterSet.setLocalityIds(filterLocalityIds.getLocalityIds());
            filterSet.setTagIds(filterTagIds.getTagIds());
        }
        else {
            filterSet.setGenreIds(new HashSet<>());
            filterSet.setLocalityIds(new HashSet<>());
            filterSet.setTagIds(new HashSet<>());
        }

        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/languageIds";

        return  webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();

    }

    @Override
    public CardResponse getLanguageCards(Long langaugeId) {
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
                .languageIds(new HashSet<>(Collections.singleton(langaugeId)))
                .build();

        FilterSet filterSet = FilterSet.builder()
                .languageIds(filterLanguageIds.getLanguageIds())
                .build();
        Optional<BlockedSet> optionalBlocked = blockedService.getBlockedSet(userId);
        if(optionalBlocked.isPresent()) {

            FilterGenreIds filterGenreIds = FilterGenreIds.builder()
                    .genreIds(homeService.getBlockedById(userId)
                            .getGenres().getAll_the_genres()
                            .stream().map(GenreData::getInjestionId)
                            .collect(Collectors.toSet()))
                    .build();
            FilterLocalityIds filterLocalityIds = FilterLocalityIds.builder()
                    .localityIds(homeService.getBlockedById(userId)
                            .getLocalities().getAll_the_localities()
                            .stream()
                            .map(LocalityData::getInjestionId)
                            .collect(Collectors.toSet()))
                    .build();

            FilterTagIds filterTagIds = FilterTagIds.builder()
                    .tagIds(homeService.getBlockedById(userId)
                            .getTags().getAll_the_tags()
                            .stream()
                            .map(TagData::getInjestionId)
                            .collect(Collectors.toSet()))
                    .build();
            filterSet.setGenreIds(filterGenreIds.getGenreIds());
            filterSet.setLocalityIds(filterLocalityIds.getLocalityIds());
            filterSet.setTagIds(filterTagIds.getTagIds());
        }
        else {
            filterSet.setGenreIds(new HashSet<>());
            filterSet.setLocalityIds(new HashSet<>());
            filterSet.setTagIds(new HashSet<>());
        }

        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/languageIds";

        return  webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
    }

    @Override
    public CardResponse getLocalitiesCards() {
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

        FilterLanguageIds filterLanguageIds = FilterLanguageIds.builder()
                .languageIds(homeService.getFollowingById(userId)
                        .getLanguages().getAll_the_languages()
                        .stream()
                        .map(LanguageData::getInjestionId)
                        .collect(Collectors.toSet()))
                .build();
        FilterSet filterSet = FilterSet.builder()
                .localityIds(filterLocalityIds.getLocalityIds())
                .languageIds(filterLanguageIds.getLanguageIds())
                .build();
        Optional<BlockedSet> optionalBlocked = blockedService.getBlockedSet(userId);
        if(optionalBlocked.isPresent()) {
            FilterGenreIds filterGenreIds = FilterGenreIds.builder()
                    .genreIds(homeService.getBlockedById(userId)
                    .getGenres().getAll_the_genres()
                    .stream()
                    .map(GenreData::getInjestionId)
                    .collect(Collectors.toSet()))
                    .build();
            FilterTagIds filterTagIds = FilterTagIds.builder()
                    .tagIds(homeService.getBlockedById(userId)
                            .getTags().getAll_the_tags()
                            .stream()
                            .map(TagData::getInjestionId)
                            .collect(Collectors.toSet()))
                    .build();
            filterSet.setGenreIds(filterGenreIds.getGenreIds());
            filterSet.setTagIds(filterTagIds.getTagIds());
        }
        else {
            filterSet.setGenreIds(new HashSet<>());
            filterSet.setTagIds(new HashSet<>());
        }

        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/localityIds";

        return  webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();

    }

    @Override
    public CardResponse getLocalityCards(Long localityId) {
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
                .localityIds(new HashSet<>(Collections.singleton(localityId)))
                .build();
        FilterLanguageIds filterLanguageIds = FilterLanguageIds.builder()
                .languageIds(homeService.getFollowingById(userId)
                        .getLanguages().getAll_the_languages()
                        .stream()
                        .map(LanguageData::getInjestionId)
                        .collect(Collectors.toSet()))
                .build();

        FilterSet filterSet = FilterSet.builder()
                .genreIds(filterLocalityIds.getLocalityIds())
                .languageIds(filterLanguageIds.getLanguageIds())
                .build();

        Optional<BlockedSet> optionalBlocked = blockedService.getBlockedSet(userId);
        if(optionalBlocked.isPresent()) {
            FilterGenreIds filterGenreIds = FilterGenreIds.builder()
                    .genreIds(homeService.getBlockedById(userId)
                            .getGenres().getAll_the_genres()
                            .stream()
                            .map(GenreData::getInjestionId)
                            .collect(Collectors.toSet()))
                    .build();
            FilterTagIds filterTagIds = FilterTagIds.builder()
                    .tagIds(homeService.getBlockedById(userId)
                            .getTags().getAll_the_tags()
                            .stream()
                            .map(TagData::getInjestionId)
                            .collect(Collectors.toSet()))
                    .build();
            filterSet.setGenreIds(filterGenreIds.getGenreIds());
            filterSet.setTagIds(filterTagIds.getTagIds());
        }
        else {
            filterSet.setGenreIds(new HashSet<>());
            filterSet.setTagIds(new HashSet<>());
        }

        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/localityIds";

        return  webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
    }

    @Override
    public CardResponse getTagsCards() {
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
        FilterLanguageIds filterLanguageIds = FilterLanguageIds.builder()
                .languageIds(homeService.getFollowingById(userId)
                        .getLanguages().getAll_the_languages()
                        .stream()
                        .map(LanguageData::getInjestionId)
                        .collect(Collectors.toSet()))
                .build();
        FilterSet filterSet = FilterSet.builder()
                .tagIds(filterTagIds.getTagIds())
                .languageIds(filterLanguageIds.getLanguageIds())
                .build();

        Optional<BlockedSet> optionalBlocked = blockedService.getBlockedSet(userId);
        if(optionalBlocked.isPresent()) {
            FilterGenreIds filterGenreIds = FilterGenreIds.builder()
                    .genreIds(homeService.getBlockedById(userId)
                            .getGenres().getAll_the_genres()
                            .stream()
                            .map(GenreData::getInjestionId)
                            .collect(Collectors.toSet()))
                    .build();
            FilterLocalityIds filterLocalityIds = FilterLocalityIds.builder()
                    .localityIds(homeService.getBlockedById(userId)
                            .getLocalities().getAll_the_localities()
                            .stream()
                            .map(LocalityData::getInjestionId)
                            .collect(Collectors.toSet()))
                    .build();
            filterSet.setGenreIds(filterGenreIds.getGenreIds());
            filterSet.setLocalityIds(filterLocalityIds.getLocalityIds());
        }
        else {
            filterSet.setGenreIds(new HashSet<>());
            filterSet.setLocalityIds(new HashSet<>());
        }

        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/tagIds";

        return  webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();

    }

    @Override
    public CardResponse getTagCards(Long tagId) {
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
                .tagIds(new HashSet<>(Collections.singleton(tagId)))
                .build();

        FilterLanguageIds filterLanguageIds = FilterLanguageIds.builder()
                .languageIds(homeService.getFollowingById(userId)
                        .getLanguages().getAll_the_languages()
                        .stream()
                        .map(LanguageData::getInjestionId)
                        .collect(Collectors.toSet()))
                .build();
        FilterSet filterSet = FilterSet.builder()
                .tagIds(filterTagIds.getTagIds())
                .languageIds(filterLanguageIds.getLanguageIds())
                .build();

        Optional<BlockedSet> optionalBlocked = blockedService.getBlockedSet(userId);
        if(optionalBlocked.isPresent()) {
            FilterGenreIds filterGenreIds = FilterGenreIds.builder()
                    .genreIds(homeService.getBlockedById(userId)
                            .getGenres().getAll_the_genres()
                            .stream()
                            .map(GenreData::getInjestionId)
                            .collect(Collectors.toSet()))
                    .build();
            FilterLocalityIds filterLocalityIds = FilterLocalityIds.builder()
                    .localityIds(homeService.getBlockedById(userId)
                            .getLocalities().getAll_the_localities()
                            .stream()
                            .map(LocalityData::getInjestionId)
                            .collect(Collectors.toSet()))
                    .build();
            filterSet.setGenreIds(filterGenreIds.getGenreIds());
            filterSet.setLocalityIds(filterLocalityIds.getLocalityIds());
        }
        else {
            filterSet.setGenreIds(new HashSet<>());
            filterSet.setLocalityIds(new HashSet<>());
        }

        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/tagIds";

        return  webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
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
