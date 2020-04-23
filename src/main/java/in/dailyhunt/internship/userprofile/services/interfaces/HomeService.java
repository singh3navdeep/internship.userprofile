package in.dailyhunt.internship.userprofile.services.interfaces;

import in.dailyhunt.internship.userprofile.client_model.response.PreferenceResponse;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;
import in.dailyhunt.internship.userprofile.client_model.response.UserDetails;

public interface HomeService {
    UserDetails getDetailsById(Long userid) throws ResourceNotFoundException;

    PreferenceResponse getFollowingById(Long userId);

    PreferenceResponse getBlockedById(Long userId);

}
