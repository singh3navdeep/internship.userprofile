package in.dailyhunt.internship.userprofile.client_model.response;

import in.dailyhunt.internship.userprofile.entities.LocalityData;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AllLocalities {

    private List<LocalityData> all_the_localities;
}
