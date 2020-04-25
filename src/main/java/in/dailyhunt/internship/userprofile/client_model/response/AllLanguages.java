package in.dailyhunt.internship.userprofile.client_model.response;

import in.dailyhunt.internship.userprofile.entities.LanguageData;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AllLanguages {
    private List<LanguageData> all_the_languages;
}
