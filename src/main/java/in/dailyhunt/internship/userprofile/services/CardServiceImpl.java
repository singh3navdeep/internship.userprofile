package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.client_model.request.FilterGenreIds;
import in.dailyhunt.internship.userprofile.client_model.response.Card;
import in.dailyhunt.internship.userprofile.client_model.response.CardResponse;
import in.dailyhunt.internship.userprofile.entities.BlockedSet;
import in.dailyhunt.internship.userprofile.entities.FollowingSet;
import in.dailyhunt.internship.userprofile.entities.GenreData;
import in.dailyhunt.internship.userprofile.exceptions.BadRequestException;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final HomeService homeService;
    private final FollowingService followingService;
    private final BlockedService blockedService;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public CardServiceImpl(HomeService homeService, FollowingService followingService,
                           BlockedService blockedService, WebClient.Builder webClientBuilder) {
        this.homeService = homeService;
        this.followingService = followingService;
        this.blockedService = blockedService;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public CardResponse getGenreCards(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<FollowingSet> optional_following = followingService.getFollowingSet(userId);
        if(!optional_following.isPresent()){
            return CardResponse.builder()
                    .cards(new ArrayList<>())
                    .build();
        }
        FilterGenreIds filterGenreIds = FilterGenreIds.builder()
                        .genreIds(homeService.getFollowingById(userId)
                        .getGenres().getAll_the_genres()
                        .stream().map(GenreData::getInjestionId)
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
}
