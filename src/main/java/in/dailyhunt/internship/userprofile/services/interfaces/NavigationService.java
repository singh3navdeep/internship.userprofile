package in.dailyhunt.internship.userprofile.services.interfaces;

import in.dailyhunt.internship.userprofile.client_model.response.NavigationResponse;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;

public interface NavigationService {

    NavigationResponse getNavigation();

    void addGenre(Long genreId) throws ResourceNotFoundException;

    void addLocality(Long localityId) throws ResourceNotFoundException;

    void addTag(Long tagId) throws ResourceNotFoundException;

    void addSource(Long sourceId) throws ResourceNotFoundException;

    void deleteGenre(Long genreId) throws ResourceNotFoundException;

    void deleteLocality(Long localityId) throws ResourceNotFoundException;

    void deleteTag(Long tagId) throws ResourceNotFoundException;

    void deleteSource(Long sourceId) throws ResourceNotFoundException;
}
