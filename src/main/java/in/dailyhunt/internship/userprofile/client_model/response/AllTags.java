package in.dailyhunt.internship.userprofile.client_model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class AllTags {
    private List<Tag> all_the_tags;
}
