package in.dailyhunt.internship.userprofile.client_model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LanguageIdList {
    private List<Long> languageIds;
}
