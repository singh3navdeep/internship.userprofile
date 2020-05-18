package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.client_model.request.*;
import in.dailyhunt.internship.userprofile.client_model.response.*;
import in.dailyhunt.internship.userprofile.entities.*;
import in.dailyhunt.internship.userprofile.exceptions.BadRequestException;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;
import in.dailyhunt.internship.userprofile.repositories.*;
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
    private final LocalityDataRepository localityDataRepository;
    private final TagDataRepository tagDataRepository;
    private final SourceDataRepository sourceDataRepository;

    @Autowired
    public CardServiceImpl(HomeService homeService, FollowingService followingService,
                           BlockedService blockedService, WebClient.Builder webClientBuilder,
                           GenreDataRepository genreDataRepository, LanguageDataRepository languageDataRepository,
                           LocalityDataRepository localityDataRepository, TagDataRepository tagDataRepository,
                           SourceDataRepository sourceDataRepository) {
        this.homeService = homeService;
        this.followingService = followingService;
        this.blockedService = blockedService;
        this.webClientBuilder = webClientBuilder;
        this.genreDataRepository = genreDataRepository;
        this.languageDataRepository = languageDataRepository;
        this.localityDataRepository = localityDataRepository;
        this.tagDataRepository = tagDataRepository;
        this.sourceDataRepository = sourceDataRepository;
    }

    @Override
    public DataCardResponse getKeywordCards(String keyword) {

        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/keyword";

        CardResponse cardResponse = webClientBuilder.build()
                .get()
                .uri(recoUrl+"/"+keyword)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
        Set<DataCard> set = new HashSet<>();
        assert cardResponse != null;
        cardResponse.getCards().forEach(card -> set.add(makeDataCardFromCard(card)));
        return DataCardResponse.builder()
                .dataCards(set)
                .build();
    }

    @Override
    public DataCardResponse getGenericCardsWithoutLogin() {
        FilterGenreIds filterGenreIds = FilterGenreIds.builder()
                .genreIds(genreDataRepository.findByGenericTrue()
                        .stream()
                        .map(GenreData::getInjestionId)
                        .collect(Collectors.toSet()))
                .build();
        FilterLanguageIds filterLanguageIds = FilterLanguageIds.builder()
                .languageIds(languageDataRepository.findAll()
                        .stream()
                        .map(LanguageData::getInjestionId)
                        .collect(Collectors.toSet()))
                .build();

        FilterSet filterSet = FilterSet.builder()
                .genreIds(filterGenreIds.getGenreIds())
                .languageIds(filterLanguageIds.getLanguageIds())
                .localityIds(new HashSet<>())
                .tagIds(new HashSet<>())
                .build();

        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/genreIds";

        CardResponse cardResponse = webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
        Set<DataCard> set = new HashSet<>();
        assert cardResponse != null;
        cardResponse.getCards().forEach(card -> set.add(makeDataCardFromCard(card)));
        return DataCardResponse.builder()
                .dataCards(set)
                .build();
    }

    @Override
    public DataCardResponse getGenreCardsWithoutLogin(Long genreId) {
        FilterGenreIds filterGenreIds = FilterGenreIds.builder()
                .genreIds(new HashSet<>(Collections.singleton(genreId)))
                .build();
        FilterLanguageIds filterLanguageIds = FilterLanguageIds.builder()
                .languageIds(languageDataRepository.findAll()
                        .stream()
                        .map(LanguageData::getInjestionId)
                        .collect(Collectors.toSet()))
                .build();

        FilterSet filterSet = FilterSet.builder()
                .genreIds(filterGenreIds.getGenreIds())
                .languageIds(filterLanguageIds.getLanguageIds())
                .localityIds(new HashSet<>())
                .tagIds(new HashSet<>())
                .build();

        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/genreIds";

        CardResponse cardResponse = webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
        Set<DataCard> set = new HashSet<>();
        assert cardResponse != null;
        cardResponse.getCards().forEach(card -> set.add(makeDataCardFromCard(card)));
        return DataCardResponse.builder()
                .dataCards(set)
                .build();
    }

    @Override
    public DataCardResponse getGenericCards() {
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

        CardResponse cardResponse = webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
        Set<DataCard> set = new HashSet<>();
        assert cardResponse != null;
        cardResponse.getCards().forEach(card -> set.add(makeDataCardFromCard(card)));
        return DataCardResponse.builder()
                .dataCards(set)
                .build();
    }

    @Override
    public DataCardResponse getGenresCards(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<FollowingSet> optional_following = followingService.getFollowingSet(userId);
        if(!optional_following.isPresent()){
            return DataCardResponse.builder()
                    .dataCards(new HashSet<>())
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

        CardResponse cardResponse = webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
        Set<DataCard> set = new HashSet<>();
        assert cardResponse != null;
        cardResponse.getCards().forEach(card -> set.add(makeDataCardFromCard(card)));
        return DataCardResponse.builder()
                .dataCards(set)
                .build();

    }

    @Override
    public DataCardResponse getGenreCards(Long genreId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<FollowingSet> optional_following = followingService.getFollowingSet(userId);
        if(!optional_following.isPresent()){
            return DataCardResponse.builder()
                    .dataCards(new HashSet<>())
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

        CardResponse cardResponse = webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
        Set<DataCard> set = new HashSet<>();
        assert cardResponse != null;
        cardResponse.getCards().forEach(card -> set.add(makeDataCardFromCard(card)));
        return DataCardResponse.builder()
                .dataCards(set)
                .build();
    }

    @Override
    public DataCardResponse getLanguagesCards() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<FollowingSet> optional_following = followingService.getFollowingSet(userId);
        if(!optional_following.isPresent()){
            return DataCardResponse.builder()
                    .dataCards(new HashSet<>())
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

        CardResponse cardResponse = webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
        Set<DataCard> set = new HashSet<>();
        assert cardResponse != null;
        cardResponse.getCards().forEach(card -> set.add(makeDataCardFromCard(card)));
        return DataCardResponse.builder()
                .dataCards(set)
                .build();
    }

    @Override
    public DataCardResponse getLanguageCards(Long langaugeId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<FollowingSet> optional_following = followingService.getFollowingSet(userId);
        if(!optional_following.isPresent()){
            return DataCardResponse.builder()
                    .dataCards(new HashSet<>())
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

        CardResponse cardResponse = webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
        Set<DataCard> set = new HashSet<>();
        assert cardResponse != null;
        cardResponse.getCards().forEach(card -> set.add(makeDataCardFromCard(card)));
        return DataCardResponse.builder()
                .dataCards(set)
                .build();
    }

    @Override
    public DataCardResponse getLocalitiesCards() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<FollowingSet> optional_following = followingService.getFollowingSet(userId);
        if(!optional_following.isPresent()){
            return DataCardResponse.builder()
                    .dataCards(new HashSet<>())
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

        CardResponse cardResponse = webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
        Set<DataCard> set = new HashSet<>();
        assert cardResponse != null;
        cardResponse.getCards().forEach(card -> set.add(makeDataCardFromCard(card)));
        return DataCardResponse.builder()
                .dataCards(set)
                .build();
    }

    @Override
    public DataCardResponse getLocalityCards(Long localityId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<FollowingSet> optional_following = followingService.getFollowingSet(userId);
        if(!optional_following.isPresent()){
            return DataCardResponse.builder()
                    .dataCards(new HashSet<>())
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

        CardResponse cardResponse = webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
        Set<DataCard> set = new HashSet<>();
        assert cardResponse != null;
        cardResponse.getCards().forEach(card -> set.add(makeDataCardFromCard(card)));
        return DataCardResponse.builder()
                .dataCards(set)
                .build();
    }

    @Override
    public DataCardResponse getTagsCards() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<FollowingSet> optional_following = followingService.getFollowingSet(userId);
        if(!optional_following.isPresent()){
            return DataCardResponse.builder()
                    .dataCards(new HashSet<>())
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

        CardResponse cardResponse = webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
        Set<DataCard> set = new HashSet<>();
        assert cardResponse != null;
        cardResponse.getCards().forEach(card -> set.add(makeDataCardFromCard(card)));
        return DataCardResponse.builder()
                .dataCards(set)
                .build();
    }

    @Override
    public DataCardResponse getTagCards(Long tagId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<FollowingSet> optional_following = followingService.getFollowingSet(userId);
        if(!optional_following.isPresent()){
            return DataCardResponse.builder()
                    .dataCards(new HashSet<>())
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

        CardResponse cardResponse = webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(filterSet), FilterSet.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
        Set<DataCard> set = new HashSet<>();
        assert cardResponse != null;
        cardResponse.getCards().forEach(card -> set.add(makeDataCardFromCard(card)));
        return DataCardResponse.builder()
                .dataCards(set)
                .build();

    }

    @Override
    public DataCardResponse getTrendingCards() {

        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/trending";

        CardResponse cardResponse = webClientBuilder.build()
                .get()
                .uri(recoUrl)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
        Set<DataCard> set = new HashSet<>();
        assert cardResponse != null;
        cardResponse.getCards().forEach(card -> set.add(makeDataCardFromCard(card)));
        return DataCardResponse.builder()
                .dataCards(set)
                .build();
    }

    @Override
    public DataCardResponse getCardsByDateRange(DateFilter dateFilter) {
        String recoUrl = "https://dailyhunt-reco-service.herokuapp.com/api/v1/filter/date-range";

        CardResponse cardResponse = webClientBuilder.build()
                .post()
                .uri(recoUrl)
                .body(Mono.just(dateFilter), DateFilter.class)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
        Set<DataCard> set = new HashSet<>();
        assert cardResponse != null;
        cardResponse.getCards().forEach(card -> set.add(makeDataCardFromCard(card)));
        return DataCardResponse.builder()
                .dataCards(set)
                .build();

    }

    public DataCard makeDataCardFromCard(Card card) {
        SourceData sourceData;
        if(card.getSourceId() == null)
            sourceData = null;
        else {
            if (sourceDataRepository.findByInjestionId(card.getSourceId()).isPresent())
                sourceData = sourceDataRepository.findByInjestionId(card.getSourceId()).get();
            else
                throw new ResourceNotFoundException("Invalid source id");
        }
        return DataCard.builder()
                .id(card.getId())
                .injestionId(card.getInjestionId())
                .sourceData(sourceData)
                .title(card.getTitle())
                .genres(new HashSet<>(genreDataRepository.findAllByInjestionIdIn(card.getGenreIds())))
                .tags(new HashSet<>(tagDataRepository.findAllByInjestionIdIn(card.getTagIds())))
                .languages(new HashSet<>(languageDataRepository.findAllByInjestionIdIn(card.getLanguageIds())))
                .localities(new HashSet<>(localityDataRepository.findAllByInjestionIdIn(card.getLocalityIds())))
                .thumbnailPath(card.getThumbnailPath())
                .trending(card.getTrending())
                .publishedAt(card.getPublishedAt())
                .build();
    }
}
