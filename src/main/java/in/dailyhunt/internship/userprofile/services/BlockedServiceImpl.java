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
    private final SourceDataRepository sourceDataRepository;

    @Autowired
    public BlockedServiceImpl(BlockedSetRepository blockedSetRepository,
                              GenreDataRepository genreDataRepository,
                              LanguageDataRepository languageDataRepository,
                              LocalityDataRepository localityDataRepository,
                              TagDataRepository tagDataRepository,
                              SourceDataRepository sourceDataRepository){
        this.blockedSetRepository = blockedSetRepository;
        this.genreDataRepository = genreDataRepository;
        this.languageDataRepository = languageDataRepository;
        this.localityDataRepository = localityDataRepository;
        this.tagDataRepository = tagDataRepository;
        this.sourceDataRepository = sourceDataRepository;
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
            Set<SourceData> sourceData = blockedSet.getSourceData();
            if(preferenceRequest.getGenreIds().isPresent())
                genreData = Stream.of(genreData, genreDataRepository.findAllByInjestionIdIn(
                        preferenceRequest.getGenreIds().get()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());

            if(preferenceRequest.getLanguageIds().isPresent())
                languageData = Stream.of(languageData, languageDataRepository.findAllByInjestionIdIn(
                        preferenceRequest.getLanguageIds().get()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());

            if(preferenceRequest.getLocalityIds().isPresent())
                localityData = Stream.of(localityData, localityDataRepository.findAllByInjestionIdIn(
                        preferenceRequest.getLocalityIds().get()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());

            if(preferenceRequest.getTagIds().isPresent())
                tagData = Stream.of(tagData, tagDataRepository.findAllByInjestionIdIn(
                        preferenceRequest.getTagIds().get()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());

            if(preferenceRequest.getSourceIds().isPresent())
                sourceData = Stream.of(sourceData, sourceDataRepository.findAllByInjestionIdIn(
                        preferenceRequest.getSourceIds().get()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());

        blockedSetRepository.save(BlockedSet.builder()
                    .userId(preferenceRequest.getUserId())
                    .genreData(genreData)
                    .languageData(languageData)
                    .localityData(localityData)
                    .tagData(tagData)
                    .sourceData(sourceData)
                    .build());
        }
        else {
            blockedSetRepository.save(BlockedSet.builder()
                    .userId(preferenceRequest.getUserId())
                    .genreData(new HashSet<>(genreDataRepository
                            .findAllByInjestionIdIn(preferenceRequest.getGenreIds()
                            .orElse(Collections.emptySet()))))
                    .languageData(new HashSet<>(languageDataRepository
                            .findAllByInjestionIdIn(preferenceRequest.getLanguageIds()
                                    .orElse(Collections.emptySet()))))
                    .localityData(new HashSet<>(localityDataRepository
                            .findAllByInjestionIdIn(preferenceRequest.getLocalityIds()
                                    .orElse(Collections.emptySet()))))
                    .tagData(new HashSet<>(tagDataRepository
                            .findAllByInjestionIdIn(preferenceRequest.getTagIds()
                                    .orElse(Collections.emptySet()))))
                    .sourceData(new HashSet<>(sourceDataRepository
                            .findAllByInjestionIdIn(preferenceRequest.getSourceIds()
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
            Set<SourceData> sourceData = blockedSet.getSourceData();
            if(preferenceRequest.getGenreIds().isPresent())
                genreData.removeAll(genreDataRepository
                        .findAllByInjestionIdIn(preferenceRequest.getGenreIds().get()));

            if(preferenceRequest.getLanguageIds().isPresent())
                languageData.removeAll(languageDataRepository
                        .findAllByInjestionIdIn(preferenceRequest.getLanguageIds().get()));

            if(preferenceRequest.getLocalityIds().isPresent())
                localityData.removeAll(localityDataRepository
                        .findAllByInjestionIdIn(preferenceRequest.getLocalityIds().get()));

            if(preferenceRequest.getTagIds().isPresent())
                tagData.removeAll(tagDataRepository
                        .findAllByInjestionIdIn(preferenceRequest.getTagIds().get()));

            if(preferenceRequest.getSourceIds().isPresent())
                sourceData.removeAll(sourceDataRepository
                        .findAllByInjestionIdIn(preferenceRequest.getSourceIds().get()));

            blockedSetRepository.save(BlockedSet.builder()
                    .userId(preferenceRequest.getUserId())
                    .genreData(genreData)
                    .languageData(languageData)
                    .localityData(localityData)
                    .tagData(tagData)
                    .sourceData(sourceData)
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
