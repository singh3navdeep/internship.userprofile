package in.dailyhunt.internship.userprofile.services.interfaces;

import in.dailyhunt.internship.userprofile.client_model.response.AllGenres;
import in.dailyhunt.internship.userprofile.client_model.response.AllLanguages;
import in.dailyhunt.internship.userprofile.client_model.response.AllLocalities;
import in.dailyhunt.internship.userprofile.client_model.response.AllTags;

public interface NewsComponentsService {

    AllGenres getAllGenres();
    AllLanguages getAllLanguages();
    AllLocalities getAllLocalities();
    AllTags getAllTags();
}
