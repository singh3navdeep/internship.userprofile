package in.dailyhunt.internship.userprofile.client_model.response;

import in.dailyhunt.internship.userprofile.entities.LocalityData;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class AllLocalities {

    private Set<LocalityData> all_the_localities;
}
