package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.client_model.response.*;
import in.dailyhunt.internship.userprofile.entities.*;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;
import in.dailyhunt.internship.userprofile.repositories.*;
import in.dailyhunt.internship.userprofile.security.services.UserPrinciple;
import in.dailyhunt.internship.userprofile.services.interfaces.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class NavigationServiceImpl implements NavigationService {

    private final NavigationSetRepository navigationSetRepository;
    private final GenreDataRepository genreDataRepository;
    private final LocalityDataRepository localityDataRepository;
    private final TagDataRepository tagDataRepository;
    private final SourceDataRepository sourceDataRepository;

    @Autowired
    public NavigationServiceImpl(NavigationSetRepository navigationSetRepository,
                                 GenreDataRepository genreDataRepository,
                                 LocalityDataRepository localityDataRepository,
                                 TagDataRepository tagDataRepository,
                                 SourceDataRepository sourceDataRepository) {
        this.navigationSetRepository = navigationSetRepository;
        this.genreDataRepository = genreDataRepository;
        this.localityDataRepository = localityDataRepository;
        this.tagDataRepository = tagDataRepository;
        this.sourceDataRepository = sourceDataRepository;
    }

    @Override
    public NavigationResponse getNavigation() throws ResourceNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<NavigationSet> optional = navigationSetRepository.findById(userId);
        if(!optional.isPresent())
            return null;
        NavigationSet navigationSet = optional.get();

        return NavigationResponse.builder()
                .genres(AllGenres.builder()
                        .all_the_genres(navigationSet.getGenreDataSet())
                        .build())
                .localities(AllLocalities.builder()
                        .all_the_localities(navigationSet.getLocalityDataSet())
                        .build())
                .tags(AllTags.builder()
                        .all_the_tags(navigationSet.getTagDataSet())
                        .build())
                .sources(AllSources.builder()
                        .all_the_sources(navigationSet.getSourceDataSet())
                        .build())
                .build();

    }

    @Override
    public void addGenre(Long genreId) throws ResourceNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<GenreData> optionalGenreData = genreDataRepository.findByInjestionId(genreId);
        if(!optionalGenreData.isPresent())
            throw new ResourceNotFoundException("This genre is not present");
        GenreData genreData = optionalGenreData.get();

        Optional<NavigationSet> optional = navigationSetRepository.findById(userId);
        if(optional.isPresent()) {
            NavigationSet navigationSet = optional.get();
            Set<GenreData> genreDataSet= navigationSet.getGenreDataSet();
            genreDataSet.add(genreData);
            navigationSet.setGenreDataSet(genreDataSet);
            navigationSetRepository.save(navigationSet);
        }
        else {
            Set<GenreData> genreDataSet = new HashSet<>();
            genreDataSet.add(genreData);
            navigationSetRepository.save(NavigationSet.builder()
                    .userId(userId)
                    .genreDataSet(genreDataSet)
                    .localityDataSet(new HashSet<>())
                    .tagDataSet(new HashSet<>())
                    .sourceDataSet(new HashSet<>())
                    .build());
        }
    }

    @Override
    public void addLocality(Long localityId) throws ResourceNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<LocalityData> optionalLocalityData = localityDataRepository.findByInjestionId(localityId);
        if(!optionalLocalityData.isPresent())
            throw new ResourceNotFoundException("This locality is not present");
        LocalityData localityData = optionalLocalityData.get();
        Optional<NavigationSet> optional = navigationSetRepository.findById(userId);
        if(optional.isPresent()) {
            NavigationSet navigationSet = optional.get();
            Set<LocalityData> localityDataSet= navigationSet.getLocalityDataSet();
            localityDataSet.add(localityData);
            navigationSet.setLocalityDataSet(localityDataSet);
            navigationSetRepository.save(navigationSet);
        }
        else {
            Set<LocalityData> localityDataSet = new HashSet<>();
            localityDataSet.add(localityData);
            navigationSetRepository.save(NavigationSet.builder()
                    .userId(userId)
                    .genreDataSet(new HashSet<>())
                    .localityDataSet(localityDataSet)
                    .tagDataSet(new HashSet<>())
                    .sourceDataSet(new HashSet<>())
                    .build());
        }

    }

    @Override
    public void addTag(Long tagId) throws ResourceNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<TagData> optionalTagData = tagDataRepository.findByInjestionId(tagId);
        if(!optionalTagData.isPresent())
            throw new ResourceNotFoundException("This tag is not present");
        TagData tagData = optionalTagData.get();
        Optional<NavigationSet> optional = navigationSetRepository.findById(userId);
        if(optional.isPresent()) {
            NavigationSet navigationSet = optional.get();
            Set<TagData> tagDataSet= navigationSet.getTagDataSet();
            tagDataSet.add(tagData);
            navigationSet.setTagDataSet(tagDataSet);
            navigationSetRepository.save(navigationSet);
        }
        else {
            Set<TagData> tagDataSet = new HashSet<>();
            tagDataSet.add(tagData);
            navigationSetRepository.save(NavigationSet.builder()
                    .userId(userId)
                    .genreDataSet(new HashSet<>())
                    .localityDataSet(new HashSet<>())
                    .tagDataSet(tagDataSet)
                    .sourceDataSet(new HashSet<>())
                    .build());
        }

    }

    @Override
    public void addSource(Long sourceId) throws ResourceNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<SourceData> optionalSourceData = sourceDataRepository.findByInjestionId(sourceId);
        if(!optionalSourceData.isPresent())
            throw new ResourceNotFoundException("This source is not present");
        SourceData sourceData = optionalSourceData.get();
        Optional<NavigationSet> optional = navigationSetRepository.findById(userId);
        if(optional.isPresent()) {
            NavigationSet navigationSet = optional.get();
            Set<SourceData> sourceDataSet= navigationSet.getSourceDataSet();
            sourceDataSet.add(sourceData);
            navigationSet.setSourceDataSet(sourceDataSet);
            navigationSetRepository.save(navigationSet);
        }
        else {
            Set<SourceData> sourceDataSet = new HashSet<>();
            sourceDataSet.add(sourceData);
            navigationSetRepository.save(NavigationSet.builder()
                    .userId(userId)
                    .genreDataSet(new HashSet<>())
                    .localityDataSet(new HashSet<>())
                    .tagDataSet(new HashSet<>())
                    .sourceDataSet(sourceDataSet)
                    .build());
        }

    }

    @Override
    public void deleteGenre(Long genreId) throws ResourceNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<GenreData> optionalGenreData = genreDataRepository.findByInjestionId(genreId);
        if(!optionalGenreData.isPresent())
            throw new ResourceNotFoundException("This genre is not present");
        GenreData genreData = optionalGenreData.get();

        Optional<NavigationSet> optionalNavigationSet = navigationSetRepository.findById(userId);
        if(!optionalNavigationSet.isPresent()){
            throw new ResourceNotFoundException("Invalid navigation");
        }
        NavigationSet navigationSet = optionalNavigationSet.get();
        Set<GenreData> genreDataSet = navigationSet.getGenreDataSet();
        genreDataSet.remove(genreData);
        navigationSetRepository.save(navigationSet);
    }

    @Override
    public void deleteLocality(Long localityId) throws ResourceNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();

        Optional<LocalityData> optionalLocalityData = localityDataRepository.findByInjestionId(localityId);
        if(!optionalLocalityData.isPresent())
            throw new ResourceNotFoundException("This locality is not present");

        LocalityData localityData = optionalLocalityData.get();

        Optional<NavigationSet> optionalNavigationSet = navigationSetRepository.findById(userId);
        if(!optionalNavigationSet.isPresent()){
            throw new ResourceNotFoundException("Invalid navigation");
        }

        NavigationSet navigationSet = optionalNavigationSet.get();
        Set<LocalityData> localityDataSet = navigationSet.getLocalityDataSet();
        localityDataSet.remove(localityData);
        navigationSetRepository.save(navigationSet);

    }

    @Override
    public void deleteTag(Long tagId) throws ResourceNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();
        Optional<TagData> optionalTagData = tagDataRepository.findByInjestionId(tagId);
        if(!optionalTagData.isPresent())
            throw new ResourceNotFoundException("This tag is not present");
        TagData tagData = optionalTagData.get();

        Optional<NavigationSet> optionalNavigationSet = navigationSetRepository.findById(userId);
        if(!optionalNavigationSet.isPresent()){
            throw new ResourceNotFoundException("Invalid navigation");
        }
        NavigationSet navigationSet = optionalNavigationSet.get();
        Set<TagData> tagDataSet = navigationSet.getTagDataSet();
        tagDataSet.remove(tagData);
        navigationSetRepository.save(navigationSet);

    }

    @Override
    public void deleteSource(Long sourceId) throws ResourceNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple user = (UserPrinciple) auth.getPrincipal();
        Long userId = user.getId();
        Optional<SourceData> optionalSourceData = sourceDataRepository.findByInjestionId(sourceId);
        if(!optionalSourceData.isPresent())
            throw new ResourceNotFoundException("This source is not present");
        SourceData sourceData = optionalSourceData.get();

        Optional<NavigationSet> optionalNavigationSet = navigationSetRepository.findById(userId);
        if(!optionalNavigationSet.isPresent()){
            throw new ResourceNotFoundException("Invalid navigation");
        }
        NavigationSet navigationSet = optionalNavigationSet.get();
        Set<SourceData> sourceDataSet = navigationSet.getSourceDataSet();
        sourceDataSet.remove(sourceData);
        navigationSetRepository.save(navigationSet);
    }
}
