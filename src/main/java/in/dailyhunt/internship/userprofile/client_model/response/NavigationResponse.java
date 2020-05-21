package in.dailyhunt.internship.userprofile.client_model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NavigationResponse {
    private AllGenres genres;
    private AllLocalities localities;
    private AllTags tags;
    private AllSources sources;
}
