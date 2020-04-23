package in.dailyhunt.internship.userprofile.client_model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PreferenceResponse {
    private AllGenres genres;
    private AllLanguages languages;
    private AllLocalities localities;
    private AllTags tags;
}
