package in.dailyhunt.internship.userprofile.client_model.response;

import in.dailyhunt.internship.userprofile.entities.SourceData;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AllSources {
    private Set<SourceData> all_the_sources;
}
