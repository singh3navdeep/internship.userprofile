package in.dailyhunt.internship.userprofile.client_model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class AllGenres {
    private List<Genre> all_the_genres;
}
