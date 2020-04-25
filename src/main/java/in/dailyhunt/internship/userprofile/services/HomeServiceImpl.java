package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.client_model.response.*;
import in.dailyhunt.internship.userprofile.entities.BlockedSet;
import in.dailyhunt.internship.userprofile.entities.FollowingSet;
import in.dailyhunt.internship.userprofile.entities.User;
import in.dailyhunt.internship.userprofile.exceptions.BadRequestException;
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
//    private final WebClient.Builder webClientBuilder;

    public HomeServiceImpl(UserRepository userRepository, FollowingService followingService,
                           BlockedService blockedService){
        this.userRepository = userRepository;
        this.followingService = followingService;
        this.blockedService = blockedService;
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
            throw new BadRequestException("users following list is empty");

        }
        else{
            FollowingSet followingSet = optional_following.get();

            return PreferenceResponse.builder()
                    .genres(AllGenres.builder()
                            .all_the_genres(followingSet.getGenreData()).build())
                    .languages(AllLanguages.builder()
                            .all_the_languages(followingSet.getLanguageData()).build())
                    .localities(AllLocalities.builder()
                            .all_the_localities(followingSet.getLocalityData()).build())
                    .tags(AllTags.builder()
                            .all_the_tags(followingSet.getTagData()).build())
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
            throw new BadRequestException("users following list is empty");
        }
        else{
            BlockedSet blockedSet = optional_blocked.get();
            return PreferenceResponse.builder()
                    .genres(AllGenres.builder()
                            .all_the_genres(blockedSet.getGenreData()).build())
                    .languages(AllLanguages.builder()
                            .all_the_languages(blockedSet.getLanguageData()).build())
                    .localities(AllLocalities.builder()
                            .all_the_localities(blockedSet.getLocalityData()).build())
                    .tags(AllTags.builder()
                            .all_the_tags(blockedSet.getTagData()).build())
                    .build();
        }
    }
}
