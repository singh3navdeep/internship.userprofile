package in.dailyhunt.internship.userprofile.client_model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class FilterSet {
    Set<Long> genreIds;
    Set<Long> languageIds;
    Set<Long> localityIds;
    Set<Long> tagIds;
    Set<Long> sourceIds;
}
