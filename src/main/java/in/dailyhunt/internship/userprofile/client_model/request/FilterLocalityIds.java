package in.dailyhunt.internship.userprofile.client_model.request;

import lombok.Data;

import java.util.Set;

@Data
public class FilterLocalityIds {
    private Set<Long> localityIds;
}
