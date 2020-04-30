package in.dailyhunt.internship.userprofile.client_model.response;

import in.dailyhunt.internship.userprofile.entities.GenreData;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AllGenres {
    private Set<GenreData> all_the_genres;
}
