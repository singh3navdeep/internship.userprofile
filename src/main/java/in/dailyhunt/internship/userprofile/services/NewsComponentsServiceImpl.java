package in.dailyhunt.internship.userprofile.services;

import in.dailyhunt.internship.userprofile.client_model.request.NewsComponentsRequest;
import in.dailyhunt.internship.userprofile.client_model.response.AllGenres;
import in.dailyhunt.internship.userprofile.client_model.response.AllLanguages;
import in.dailyhunt.internship.userprofile.client_model.response.AllLocalities;
import in.dailyhunt.internship.userprofile.client_model.response.AllTags;
import in.dailyhunt.internship.userprofile.entities.GenreData;
import in.dailyhunt.internship.userprofile.entities.LanguageData;
import in.dailyhunt.internship.userprofile.entities.LocalityData;
import in.dailyhunt.internship.userprofile.entities.TagData;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;
import in.dailyhunt.internship.userprofile.repositories.GenreDataRepository;
import in.dailyhunt.internship.userprofile.repositories.LanguageDataRepository;
import in.dailyhunt.internship.userprofile.repositories.LocalityDataRepository;
import in.dailyhunt.internship.userprofile.repositories.TagDataRepository;
import in.dailyhunt.internship.userprofile.services.interfaces.NewsComponentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTML;
import java.util.HashSet;
import java.util.Optional;


@Service
public class NewsComponentsServiceImpl implements NewsComponentsService {

    private final GenreDataRepository genreDataRepository;
    private final LanguageDataRepository languageDataRepository;
    private final LocalityDataRepository localityDataRepository;
    private final TagDataRepository tagDataRepository;
    @Autowired
    public NewsComponentsServiceImpl(GenreDataRepository genreDataRepository,
                                     LanguageDataRepository languageDataRepository,
                                     LocalityDataRepository localityDataRepository,
                                     TagDataRepository tagDataRepository) {
        this.genreDataRepository = genreDataRepository;
        this.languageDataRepository = languageDataRepository;
        this.localityDataRepository = localityDataRepository;
        this.tagDataRepository = tagDataRepository;
    }

    @Override
    public AllGenres getAllGenres() {

        return AllGenres.builder()
                .all_the_genres(new HashSet<>(genreDataRepository.findAll()))
                .build();
    }

    @Override
    public void addGenre(NewsComponentsRequest newsComponentsRequest) {
        genreDataRepository.save(GenreData.builder()
                .injestionId(newsComponentsRequest.getId())
                .name(newsComponentsRequest.getName())
                .build());
    }

    public void deleteGenre(Long id) {
        Optional<GenreData> optionalGenreData = genreDataRepository.findByInjestionId(id);
        if(optionalGenreData.isPresent()) {
            GenreData genreData = optionalGenreData.get();
            genreDataRepository.delete(genreData);
        }
        else {
            throw new ResourceNotFoundException("genre data not found");
        }
    }

    @Override
    public AllLanguages getAllLanguages() {

        return AllLanguages.builder()
                .all_the_languages(new HashSet<>(languageDataRepository.findAll()))
                .build();
    }

    @Override
    public void addLanguage(NewsComponentsRequest newsComponentsRequest) {
        languageDataRepository.save(LanguageData.builder()
                .injestionId(newsComponentsRequest.getId())
                .name(newsComponentsRequest.getName())
                .build());
    }
    public void deleteLanguage(Long id) {
        Optional<LanguageData> optionalLanguageData = languageDataRepository.findByInjestionId(id);
        if(optionalLanguageData.isPresent()) {
            LanguageData languageData = optionalLanguageData.get();
            languageDataRepository.delete(languageData);
        }
        else {
            throw new ResourceNotFoundException("language not found");
        }
    }

    @Override
    public AllLocalities getAllLocalities() {

        return AllLocalities.builder()
                .all_the_localities(new HashSet<>(localityDataRepository.findAll()))
                .build();
    }

    @Override
    public void addLocality(NewsComponentsRequest newsComponentsRequest) {
        localityDataRepository.save(LocalityData.builder()
                .injestionId(newsComponentsRequest.getId())
                .name(newsComponentsRequest.getName())
                .build());
    }
    public void deleteLocality(Long id) {
        Optional<LocalityData> optionalLocalityData = localityDataRepository.findByInjestionId(id);
        if(optionalLocalityData.isPresent()) {
            LocalityData localityData = optionalLocalityData.get();
            localityDataRepository.delete(localityData);
        }
        else {
            throw new ResourceNotFoundException("locality not found");
        }
    }

    @Override
    public AllTags getAllTags() {

        return AllTags.builder()
                .all_the_tags(new HashSet<>(tagDataRepository.findAll()))
                .build();
    }

    @Override
    public void addTag(NewsComponentsRequest newsComponentsRequest) {
        tagDataRepository.save(TagData.builder()
                .injestionId(newsComponentsRequest.getId())
                .name(newsComponentsRequest.getName())
                .build());
    }
    public void deleteTag(Long id) {
        Optional<TagData> optionalTagData = tagDataRepository.findByInjestionId(id);
        if(optionalTagData.isPresent()) {
            TagData tagData = optionalTagData.get();
            tagDataRepository.delete(tagData);
        }
        else {
            throw new ResourceNotFoundException("tag not found");
        }
    }

}
