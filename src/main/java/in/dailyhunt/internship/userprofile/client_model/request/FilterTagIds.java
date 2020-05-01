package in.dailyhunt.internship.userprofile.client_model.request;

import lombok.Data;

import java.util.Set;

@Data
public class FilterTagIds {
    private Set<Long> tagIds;
}
