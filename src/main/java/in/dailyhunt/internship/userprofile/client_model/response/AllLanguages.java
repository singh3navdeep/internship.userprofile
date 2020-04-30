package in.dailyhunt.internship.userprofile.client_model.response;

import in.dailyhunt.internship.userprofile.entities.LanguageData;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class AllLanguages {
    private Set<LanguageData> all_the_languages;
}
