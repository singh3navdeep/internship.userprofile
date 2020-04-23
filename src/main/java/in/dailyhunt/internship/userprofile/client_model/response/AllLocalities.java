package in.dailyhunt.internship.userprofile.client_model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class AllLocalities {

    private List<Locality> all_the_localities;
}
