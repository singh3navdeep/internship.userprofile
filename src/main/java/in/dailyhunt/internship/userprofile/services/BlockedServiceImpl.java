package in.dailyhunt.internship.userprofile.services;
import in.dailyhunt.internship.userprofile.client_model.request.PreferenceRequest;
import in.dailyhunt.internship.userprofile.entities.*;
import in.dailyhunt.internship.userprofile.exceptions.BadRequestException;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;
import in.dailyhunt.internship.userprofile.repositories.*;
import in.dailyhunt.internship.userprofile.services.interfaces.BlockedService;
import in.dailyhunt.internship.userprofile.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BlockedServiceImpl implements BlockedService {

    private final BlockedSetRepository blockedSetRepository;
    private final GenreDataRepository genreDataRepository;
    private final LanguageDataRepository languageDataRepository;
    private final LocalityDataRepository localityDataRepository;
    private final TagDataRepository tagDataRepository;

    @Autowired
    public BlockedServiceImpl(BlockedSetRepository blockedSetRepository,
                              GenreDataRepository genreDataRepository,
                              LanguageDataRepository languageDataRepository,
                              LocalityDataRepository localityDataRepository,
                              TagDataRepository tagDataRepository){
        this.blockedSetRepository = blockedSetRepository;
        this.genreDataRepository = genreDataRepository;
        this.languageDataRepository = languageDataRepository;
        this.localityDataRepository = localityDataRepository;
        this.tagDataRepository = tagDataRepository;
    }
    @Override
    @Transactional
    public void addBlocked(PreferenceRequest preferenceRequest) throws ResourceNotFoundException {
        Optional<BlockedSet> optional = blockedSetRepository.findById(preferenceRequest.getUserId());
        if(optional.isPresent()){
            BlockedSet blockedSet = optional.get();
            Set<GenreData> genreData = blockedSet.getGenreData();
            Set<LanguageData> languageData = blockedSet.getLanguageData();
            Set<LocalityData> localityData = blockedSet.getLocalityData();
            Set<TagData> tagData = blockedSet.getTagData();
            if(preferenceRequest.getGenreIds().isPresent())
                genreData = Stream.of(genreData, genreDataRepository.findAllByInjestionId(
                        preferenceRequest.getGenreIds().get()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());

            if(preferenceRequest.getLanguageIds().isPresent())
                languageData = Stream.of(languageData, languageDataRepository.findAllByInjestionId(
                        preferenceRequest.getLanguageIds().get()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());

            if(preferenceRequest.getLocalityIds().isPresent())
                localityData = Stream.of(localityData, localityDataRepository.findAllByInjestionId(
                        preferenceRequest.getLocalityIds().get()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());

            if(preferenceRequest.getTagIds().isPresent())
                tagData = Stream.of(tagData, tagDataRepository.findAllByInjestionId(
                        preferenceRequest.getTagIds().get()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());
            blockedSetRepository.save(BlockedSet.builder()
                    .userId(preferenceRequest.getUserId())
                    .genreData(genreData)
                    .languageData(languageData)
                    .localityData(localityData)
                    .tagData(tagData)
                    .build());
        }
        else {
            blockedSetRepository.save(BlockedSet.builder()
                    .userId(preferenceRequest.getUserId())
                    .genreData(new HashSet<>(genreDataRepository
                            .findAllByInjestionId(preferenceRequest.getGenreIds()
                            .orElse(Collections.emptySet()))))
                    .languageData(new HashSet<>(languageDataRepository
                            .findAllByInjestionId(preferenceRequest.getLanguageIds()
                                    .orElse(Collections.emptySet()))))
                    .localityData(new HashSet<>(localityDataRepository
                            .findAllByInjestionId(preferenceRequest.getLocalityIds()
                                    .orElse(Collections.emptySet()))))
                    .tagData(new HashSet<>(tagDataRepository
                            .findAllByInjestionId(preferenceRequest.getTagIds()
                                    .orElse(Collections.emptySet()))))
                    .build());
        }
    }
    @Override
    @Transactional
    public void deleteBlocked(PreferenceRequest preferenceRequest) throws ResourceNotFoundException {
        Optional<BlockedSet> optional = blockedSetRepository.findById(preferenceRequest.getUserId());
        if(optional.isPresent()){
            BlockedSet blockedSet = optional.get();
            Set<GenreData> genreData = blockedSet.getGenreData();
            Set<LanguageData> languageData = blockedSet.getLanguageData();
            Set<LocalityData> localityData = blockedSet.getLocalityData();
            Set<TagData> tagData = blockedSet.getTagData();
            if(preferenceRequest.getGenreIds().isPresent())
                genreData.removeAll(genreDataRepository
                        .findAllByInjestionId(preferenceRequest.getGenreIds().get()));

            if(preferenceRequest.getLanguageIds().isPresent())
                languageData.removeAll(languageDataRepository
                        .findAllByInjestionId(preferenceRequest.getLanguageIds().get()));

            if(preferenceRequest.getLocalityIds().isPresent())
                localityData.removeAll(localityDataRepository
                        .findAllByInjestionId(preferenceRequest.getLocalityIds().get()));

            if(preferenceRequest.getTagIds().isPresent())
                tagData.removeAll(tagDataRepository
                        .findAllByInjestionId(preferenceRequest.getTagIds().get()));
            blockedSetRepository.save(BlockedSet.builder()
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
    public Optional<BlockedSet> getBlockedSet(Long userId) {
        return blockedSetRepository.findById(userId);
    }

}
