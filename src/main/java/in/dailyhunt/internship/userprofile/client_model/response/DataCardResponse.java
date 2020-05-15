package in.dailyhunt.internship.userprofile.client_model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class DataCardResponse {
    private Set<DataCard> dataCards;
}
