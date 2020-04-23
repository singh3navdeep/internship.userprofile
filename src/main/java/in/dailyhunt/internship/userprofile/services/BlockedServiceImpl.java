package in.dailyhunt.internship.userprofile.services;
import in.dailyhunt.internship.userprofile.client_model.request.PreferenceRequest;
import in.dailyhunt.internship.userprofile.entities.BlockedSet;
import in.dailyhunt.internship.userprofile.entities.FollowingSet;
import in.dailyhunt.internship.userprofile.exceptions.BadRequestException;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;
import in.dailyhunt.internship.userprofile.repositories.BlockedSetRepository;
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

    private BlockedSetRepository blockedSetRepository;

    @Autowired
    public BlockedServiceImpl(BlockedSetRepository blockedSetRepository){

        this.blockedSetRepository = blockedSetRepository;
    }
    @Override
    @Transactional
    public void addBlocked(PreferenceRequest preferenceRequest) throws ResourceNotFoundException {
        Optional<BlockedSet> optional = blockedSetRepository.findById(preferenceRequest.getUserId());
        if(optional.isPresent()){
            BlockedSet blockedSet = optional.get();
            List<Long> genres = blockedSet.getGenreIds();
            List<Long> languages = blockedSet.getLanguageIds();
            List<Long> localities = blockedSet.getLocalityIds();
            List<Long> tags = blockedSet.getTagIds();
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
            blockedSetRepository.save(BlockedSet.builder()
                    .userId(preferenceRequest.getUserId())
                    .genreIds(genres)
                    .languageIds(languages)
                    .localityIds(localities)
                    .tagIds(tags)
                    .build());
        }
        else {
            blockedSetRepository.save(BlockedSet.builder()
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
    public void deleteBlocked(PreferenceRequest preferenceRequest) throws ResourceNotFoundException {
        Optional<BlockedSet> optional = blockedSetRepository.findById(preferenceRequest.getUserId());
        if(optional.isPresent()){
            BlockedSet blockedSet = optional.get();
            List<Long> genres = blockedSet.getGenreIds();
            List<Long> languages = blockedSet.getLanguageIds();
            List<Long> localities = blockedSet.getLocalityIds();
            List<Long> tags = blockedSet.getTagIds();
            if(preferenceRequest.getGenreIds() != null)
                genres.removeAll(preferenceRequest.getGenreIds());

            if(preferenceRequest.getLanguageIds() != null)
                languages.removeAll(preferenceRequest.getLanguageIds());

            if(preferenceRequest.getLocalityIds() != null)
                localities.removeAll(preferenceRequest.getLocalityIds());

            if(preferenceRequest.getTagIds() != null)
                tags.removeAll(preferenceRequest.getTagIds());
            blockedSetRepository.save(BlockedSet.builder()
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
    public Optional<BlockedSet> getBlockedSet(Long userId) {
        return blockedSetRepository.findById(userId);
    }

}
