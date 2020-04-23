package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.client_model.request.PreferenceRequest;
import in.dailyhunt.internship.userprofile.entities.FollowingSet;
import in.dailyhunt.internship.userprofile.entities.User;
import in.dailyhunt.internship.userprofile.exceptions.BadRequestException;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;
import in.dailyhunt.internship.userprofile.repositories.FollowingSetRepository;
import in.dailyhunt.internship.userprofile.services.interfaces.FollowingService;
import in.dailyhunt.internship.userprofile.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FollowingServiceImpl implements FollowingService {

    private final FollowingSetRepository followingSetRepository;

    @Autowired
    public FollowingServiceImpl(FollowingSetRepository followingSetRepository){
        this.followingSetRepository = followingSetRepository;
    }

    @Override
    @Transactional
    public void addFollowing(PreferenceRequest preferenceRequest) throws ResourceNotFoundException {
         Optional<FollowingSet> optional = followingSetRepository.findById(preferenceRequest.getUserId());
         if(optional.isPresent()){
             FollowingSet followingSet = optional.get();
             List<Long> genres = followingSet.getGenreIds();
             List<Long> languages = followingSet.getLanguageIds();
             List<Long> localities = followingSet.getLocalityIds();
             List<Long> tags = followingSet.getTagIds();
             if(preferenceRequest.getGenreIds() != null)
                 genres = Stream.of(genres, preferenceRequest.getGenreIds())
                         .flatMap(Collection::stream)
                         .collect(Collectors.toList());

             if(preferenceRequest.getLanguageIds() != null)
                 languages = Stream.of(languages, preferenceRequest.getLanguageIds())
                         .flatMap(Collection::stream)
                         .collect(Collectors.toList());

             if(preferenceRequest.getLocalityIds() != null)
                 localities = Stream.of(localities, preferenceRequest.getLocalityIds())
                         .flatMap(Collection::stream)
                         .collect(Collectors.toList());

             if(preferenceRequest.getTagIds() != null)
                 tags = Stream.of(tags, preferenceRequest.getTagIds())
                         .flatMap(Collection::stream)
                         .collect(Collectors.toList());
             followingSetRepository.save(FollowingSet.builder()
                     .userId(preferenceRequest.getUserId())
                     .genreIds(genres)
                     .languageIds(languages)
                     .localityIds(localities)
                     .tagIds(tags)
                     .build());
         }
         else {
             followingSetRepository.save(FollowingSet.builder()
                     .userId(preferenceRequest.getUserId())
                     .genreIds(preferenceRequest.getGenreIds())
                     .languageIds(preferenceRequest.getLanguageIds())
                     .localityIds(preferenceRequest.getLocalityIds())
                     .tagIds(preferenceRequest.getTagIds())
                     .build());
         }
    }

    @Override
    @Transactional
    public void deleteFollowing(PreferenceRequest preferenceRequest) throws ResourceNotFoundException {
        Optional<FollowingSet> optional = followingSetRepository.findById(preferenceRequest.getUserId());
        if(optional.isPresent()){
            FollowingSet followingSet = optional.get();
            List<Long> genres = followingSet.getGenreIds();
            List<Long> languages = followingSet.getLanguageIds();
            List<Long> localities = followingSet.getLocalityIds();
            List<Long> tags = followingSet.getTagIds();
            if(preferenceRequest.getGenreIds() != null)
                genres.removeAll(preferenceRequest.getGenreIds());

            if(preferenceRequest.getLanguageIds() != null)
                languages.removeAll(preferenceRequest.getLanguageIds());

            if(preferenceRequest.getLocalityIds() != null)
                localities.removeAll(preferenceRequest.getLocalityIds());

            if(preferenceRequest.getTagIds() != null)
                tags.removeAll(preferenceRequest.getTagIds());
            followingSetRepository.save(FollowingSet.builder()
                    .userId(preferenceRequest.getUserId())
                    .genreIds(genres)
                    .languageIds(languages)
                    .localityIds(localities)
                    .tagIds(tags)
                    .build());
        }
        else
            throw new BadRequestException("There is nothing in following");
    }

    @Override
    public Optional<FollowingSet> getFollowingSet(Long userId) {
        return followingSetRepository.findById(userId);
    }
}
