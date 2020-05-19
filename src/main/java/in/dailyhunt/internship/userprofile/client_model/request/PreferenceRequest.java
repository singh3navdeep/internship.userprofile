package in.dailyhunt.internship.userprofile.client_model.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
@Builder
public class PreferenceRequest {

    private Long userId;

    private Optional<Set<Long>> genreIds;

    private Optional<Set<Long>> languageIds;

    private Optional<Set<Long>> localityIds;

    private Optional<Set<Long>> tagIds;

    private Optional<Set<Long>> sourceIds;

}
