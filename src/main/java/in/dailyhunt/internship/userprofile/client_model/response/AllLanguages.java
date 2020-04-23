package in.dailyhunt.internship.userprofile.client_model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class AllLanguages {
    private List<Language> all_the_languages;
}
