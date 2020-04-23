package in.dailyhunt.internship.userprofile.services.interfaces;

import in.dailyhunt.internship.userprofile.client_model.request.PreferenceRequest;
import in.dailyhunt.internship.userprofile.entities.FollowingSet;

import java.util.Optional;

public interface FollowingService {
    void addFollowing(PreferenceRequest preferenceRequest);

    void deleteFollowing(PreferenceRequest preferenceRequest);

    Optional<FollowingSet> getFollowingSet(Long userId);
}
