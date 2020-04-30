package in.dailyhunt.internship.userprofile.client_model.response;

import in.dailyhunt.internship.userprofile.entities.TagData;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class AllTags {
    private Set<TagData> all_the_tags;
}
