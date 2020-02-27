package in.dailyhunt.internship.userprofile.services.interfaces;

import in.dailyhunt.internship.userprofile.entities.Topic;
import in.dailyhunt.internship.userprofile.exceptions.ResourceNotFoundException;
import in.dailyhunt.internship.userprofile.client_model.response.UserDetails;

import java.util.List;

public interface HomeService {
    UserDetails getDetailsById(Long userid) throws ResourceNotFoundException;

    List<Topic> getFollowingById(Long userId);

    List<Topic> getBlockedById(Long userId);
}
