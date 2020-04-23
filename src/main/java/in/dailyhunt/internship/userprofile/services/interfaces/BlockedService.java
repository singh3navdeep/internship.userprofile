package in.dailyhunt.internship.userprofile.services.interfaces;

import in.dailyhunt.internship.userprofile.client_model.request.PreferenceRequest;
import in.dailyhunt.internship.userprofile.entities.BlockedSet;

import java.util.Optional;

public interface BlockedService {

    void addBlocked(PreferenceRequest preferenceRequest);

    void deleteBlocked(PreferenceRequest preferenceRequest);

    Optional<BlockedSet> getBlockedSet(Long userId);
}
