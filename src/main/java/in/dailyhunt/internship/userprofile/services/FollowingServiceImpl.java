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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
             List<GenreData> genreData = followingSet.getGenreData();
             List<LanguageData> languageData = followingSet.getLanguageData();
             List<LocalityData> localityData = followingSet.getLocalityData();
             List<TagData> tagData = followingSet.getTagData();
             if(preferenceRequest.getGenreIds() != null)
                 genreData = Stream.of(genreData, genreDataRepository.findAllById(preferenceRequest.getGenreIds()))
                         .flatMap(Collection::stream)
                         .collect(Collectors.toList());

             if(preferenceRequest.getLanguageIds() != null)
                 languageData = Stream.of(languageData, languageDataRepository.findAllById(
                         preferenceRequest.getLanguageIds()))
                         .flatMap(Collection::stream)
                         .collect(Collectors.toList());

             if(preferenceRequest.getLocalityIds() != null)
                 localityData = Stream.of(localityData, localityDataRepository.findAllById(
                         preferenceRequest.getLocalityIds()))
                         .flatMap(Collection::stream)
                         .collect(Collectors.toList());

             if(preferenceRequest.getTagIds() != null)
                 tagData = Stream.of(tagData, tagDataRepository.findAllById(
                         preferenceRequest.getTagIds()))
                         .flatMap(Collection::stream)
                         .collect(Collectors.toList());
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
                     .genreData(genreDataRepository.findAllById(preferenceRequest.getGenreIds()))
                     .languageData(languageDataRepository.findAllById(preferenceRequest.getLanguageIds()))
                     .localityData(localityDataRepository.findAllById(preferenceRequest.getLocalityIds()))
                     .tagData(tagDataRepository.findAllById(preferenceRequest.getTagIds()))
                     .build());
         }
    }

    @Override
    @Transactional
    public void deleteFollowing(PreferenceRequest preferenceRequest) throws ResourceNotFoundException {
        Optional<FollowingSet> optional = followingSetRepository.findById(preferenceRequest.getUserId());
        if(optional.isPresent()){
            FollowingSet followingSet = optional.get();
            List<GenreData> genreData = followingSet.getGenreData();
            List<LanguageData> languageData = followingSet.getLanguageData();
            List<LocalityData> localityData = followingSet.getLocalityData();
            List<TagData> tagData = followingSet.getTagData();
            if(preferenceRequest.getGenreIds() != null)
                genreData.removeAll(genreDataRepository.findAllById(
                        preferenceRequest.getGenreIds()));

            if(preferenceRequest.getLanguageIds() != null)
                languageData.removeAll(languageDataRepository.findAllById(
                        preferenceRequest.getLanguageIds()));

            if(preferenceRequest.getLocalityIds() != null)
                localityData.removeAll(localityDataRepository.findAllById(
                        preferenceRequest.getLocalityIds()));

            if(preferenceRequest.getTagIds() != null)
                tagData.removeAll(tagDataRepository.findAllById(
                        preferenceRequest.getTagIds()));
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
