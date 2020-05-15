package in.dailyhunt.internship.userprofile.client_model.response;

import in.dailyhunt.internship.userprofile.entities.GenreData;
import in.dailyhunt.internship.userprofile.entities.LanguageData;
import in.dailyhunt.internship.userprofile.entities.LocalityData;
import in.dailyhunt.internship.userprofile.entities.TagData;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Builder
public class DataCard {

    private Long id;

    private Long injestionId;

    private String title;

    private Set<TagData> tags;

    private Set<LocalityData> localities;

    private Set<GenreData> genres;

    private Set<LanguageData> languages;

    private String thumbnailPath;

    private Boolean trending;

    private Date publishedAt;
}
