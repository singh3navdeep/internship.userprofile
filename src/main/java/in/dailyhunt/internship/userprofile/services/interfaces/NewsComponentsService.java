package in.dailyhunt.internship.userprofile.services.interfaces;

import in.dailyhunt.internship.userprofile.client_model.request.NewsComponentsRequest;
import in.dailyhunt.internship.userprofile.client_model.response.AllGenres;
import in.dailyhunt.internship.userprofile.client_model.response.AllLanguages;
import in.dailyhunt.internship.userprofile.client_model.response.AllLocalities;
import in.dailyhunt.internship.userprofile.client_model.response.AllTags;
import in.dailyhunt.internship.userprofile.entities.GenreData;
import in.dailyhunt.internship.userprofile.entities.LanguageData;
import in.dailyhunt.internship.userprofile.entities.LocalityData;
import in.dailyhunt.internship.userprofile.entities.TagData;

import java.util.List;

public interface NewsComponentsService {

    AllGenres getAllGenres();
    void addGenre(NewsComponentsRequest newsComponentsRequest);
    void deleteGenre(Long id);
    AllLanguages getAllLanguages();
    void addLanguage(NewsComponentsRequest newsComponentsRequest);
    void deleteLanguage(Long id);
    AllLocalities getAllLocalities();
    void addLocality(NewsComponentsRequest newsComponentsRequest);
    void deleteLocality(Long id);
    AllTags getAllTags();
    void addTag(NewsComponentsRequest newsComponentsRequest);
    void deleteTag(Long id);
}
