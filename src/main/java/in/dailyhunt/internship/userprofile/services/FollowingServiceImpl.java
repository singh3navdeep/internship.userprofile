package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.client_model.request.PreferenceRequest;
import in.dailyhunt.internship.userprofile.entities.*;
import in.dailyhunt.internship.userprofile.exceptions.BadRequestException;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;
import in.dailyhunt.internship.userprofile.repositories.*;
import in.dailyhunt.internship.userprofile.services.interfaces.FollowingService;
import in.dailyhunt.internship.userprofile.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FollowingServiceImpl implements FollowingService {

    private final FollowingSetRepository followingSetRepository;
    private final GenreDataRepository genreDataRepository;
    private final LanguageDataRepository languageDataRepository;
    private final LocalityDataRepository localityDataRepository;
    private final TagDataRepository tagDataRepository;

    @Autowired
    public FollowingServiceImpl(FollowingSetRepository followingSetRepository,
                                GenreDataRepository genreDataRepository,
                                LanguageDataRepository languageDataRepository,
                                LocalityDataRepository localityDataRepository,
                                TagDataRepository tagDataRepository){
        this.followingSetRepository = followingSetRepository;
        this.genreDataRepository = genreDataRepository;
        this.languageDataRepository = languageDataRepository;
        this.localityDataRepository = localityDataRepository;
        this.tagDataRepository = tagDataRepository;
    }

    @Override
    @Transactional
    public void addFollowing(PreferenceRequest preferenceRequest) throws ResourceNotFoundException {
         Optional<FollowingSet> optional = followingSetRepository.findById(preferenceRequest.getUserId());
         if(optional.isPresent()){
             FollowingSet followingSet = optional.get();
             Set<GenreData> genreData = followingSet.getGenreData();
             Set<LanguageData> languageData = followingSet.getLanguageData();
             Set<LocalityData> localityData = followingSet.getLocalityData();
             Set<TagData> tagData = followingSet.getTagData();
             if(preferenceRequest.getGenreIds().isPresent())
                 genreData = Stream.of(genreData, genreDataRepository
                         .findAllById(preferenceRequest.getGenreIds().get()))
                         .flatMap(Collection::stream)
                         .collect(Collectors.toSet());

             if(preferenceRequest.getLanguageIds().isPresent())
                 languageData = Stream.of(languageData, languageDataRepository
                         .findAllById(preferenceRequest.getLanguageIds().get()))
                         .flatMap(Collection::stream)
                         .collect(Collectors.toSet());

             if(preferenceRequest.getLocalityIds().isPresent())
                 localityData = Stream.of(localityData, localityDataRepository
                         .findAllById(preferenceRequest.getLocalityIds().get()))
                         .flatMap(Collection::stream)
                         .collect(Collectors.toSet());

             if(preferenceRequest.getTagIds().isPresent())
                 tagData = Stream.of(tagData, tagDataRepository
                         .findAllById(preferenceRequest.getTagIds().get()))
                         .flatMap(Collection::stream)
                         .collect(Collectors.toSet());
             followingSetRepository.save(FollowingSet.builder()
                     .userId(preferenceRequest.getUserId())
                     .genreData(genreData)
                     .languageData(languageData)
                     .localityData(localityData)
                     .tagData(tagData)
                     .build());
         }
         else {
             followingSetRepository.save(FollowingSet.builder()
                     .userId(preferenceRequest.getUserId())
                     .genreData(new HashSet<>(genreDataRepository
                             .findAllById(preferenceRequest.getGenreIds()
                             .orElse(Collections.emptySet()))))
                     .languageData(new HashSet<>(languageDataRepository
                             .findAllById(preferenceRequest.getLanguageIds()
                             .orElse(Collections.emptySet()))))
                     .localityData(new HashSet<>(localityDataRepository
                             .findAllById(preferenceRequest.getLocalityIds()
                             .orElse(Collections.emptySet()))))
                     .tagData(new HashSet<>(tagDataRepository
                             .findAllById(preferenceRequest.getTagIds()
                             .orElse(Collections.emptySet()))))
                     .build());
         }
    }

    @Override
    @Transactional
    public void deleteFollowing(PreferenceRequest preferenceRequest) throws ResourceNotFoundException {
        Optional<FollowingSet> optional = followingSetRepository.findById(preferenceRequest.getUserId());
        if(optional.isPresent()){
            FollowingSet followingSet = optional.get();
            Set<GenreData> genreData = followingSet.getGenreData();
            Set<LanguageData> languageData = followingSet.getLanguageData();
            Set<LocalityData> localityData = followingSet.getLocalityData();
            Set<TagData> tagData = followingSet.getTagData();
            if(preferenceRequest.getGenreIds().isPresent())
                genreData.removeAll(genreDataRepository.findAllById(
                        preferenceRequest.getGenreIds().get()));

            if(preferenceRequest.getLanguageIds().isPresent())
                languageData.removeAll(languageDataRepository.findAllById(
                        preferenceRequest.getLanguageIds().get()));

            if(preferenceRequest.getLocalityIds().isPresent())
                localityData.removeAll(localityDataRepository.findAllById(
                        preferenceRequest.getLocalityIds().get()));

            if(preferenceRequest.getTagIds().isPresent())
                tagData.removeAll(tagDataRepository.findAllById(
                        preferenceRequest.getTagIds().get()));
            followingSetRepository.save(FollowingSet.builder()
                    .userId(preferenceRequest.getUserId())
                    .genreData(genreData)
                    .languageData(languageData)
                    .localityData(localityData)
                    .tagData(tagData)
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
