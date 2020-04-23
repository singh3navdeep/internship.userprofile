package in.dailyhunt.internship.userprofile.client_model.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PreferenceRequest {

    private Long userId;

    private List<Long> genreIds;

    private List<Long> languageIds;

    private List<Long> localityIds;

    private List<Long> tagIds;

}
