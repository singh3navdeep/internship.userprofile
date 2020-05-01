package in.dailyhunt.internship.userprofile.client_model.request;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class FilterGenreIds {
    private Set<Long> genreIds;
}
