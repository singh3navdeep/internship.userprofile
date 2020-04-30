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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
            List<GenreData> genreData = blockedSet.getGenreData();
            List<LanguageData> languageData = blockedSet.getLanguageData();
            List<LocalityData> localityData = blockedSet.getLocalityData();
            List<TagData> tagData = blockedSet.getTagData();
            if(!preferenceRequest.getGenreIds().isEmpty())
                genreData = Stream.of(genreData, genreDataRepository.findAllById(
                        preferenceRequest.getGenreIds()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());

            if(!preferenceRequest.getLanguageIds().isEmpty())
                languageData = Stream.of(languageData, languageDataRepository.findAllById(
                        preferenceRequest.getLanguageIds()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());

            if(!preferenceRequest.getLocalityIds().isEmpty())
                localityData = Stream.of(localityData, localityDataRepository.findAllById(
                        preferenceRequest.getLocalityIds()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());

            if(!preferenceRequest.getTagIds().isEmpty())
                tagData = Stream.of(tagData, tagDataRepository.findAllById(
                        preferenceRequest.getTagIds()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
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
                    .genreData(genreDataRepository.findAllById(preferenceRequest.getGenreIds()))
                    .languageData(languageDataRepository.findAllById(preferenceRequest.getLanguageIds()))
                    .localityData(localityDataRepository.findAllById(preferenceRequest.getLocalityIds()))
                    .tagData(tagDataRepository.findAllById(preferenceRequest.getTagIds()))
                    .build());
        }
    }
    @Override
    @Transactional
    public void deleteBlocked(PreferenceRequest preferenceRequest) throws ResourceNotFoundException {
        Optional<BlockedSet> optional = blockedSetRepository.findById(preferenceRequest.getUserId());
        if(optional.isPresent()){
            BlockedSet blockedSet = optional.get();
            List<GenreData> genreData = blockedSet.getGenreData();
            List<LanguageData> languageData = blockedSet.getLanguageData();
            List<LocalityData> localityData = blockedSet.getLocalityData();
            List<TagData> tagData = blockedSet.getTagData();
            if(preferenceRequest.getGenreIds() != null)
                genreData.removeAll(genreDataRepository.findAllById(preferenceRequest.getGenreIds()));

            if(preferenceRequest.getLanguageIds() != null)
                languageData.removeAll(languageDataRepository.findAllById(preferenceRequest.getLanguageIds()));

            if(preferenceRequest.getLocalityIds() != null)
                localityData.removeAll(localityDataRepository.findAllById(preferenceRequest.getLocalityIds()));

            if(preferenceRequest.getTagIds() != null)
                tagData.removeAll(tagDataRepository.findAllById(preferenceRequest.getTagIds()));
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
