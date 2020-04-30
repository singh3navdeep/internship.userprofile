package in.dailyhunt.internship.userprofile.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
@Table(name = "FOLLOWING")
public class FollowingSet {

    @Id
    @NotNull
    private Long userId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_following_genres",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<GenreData> genreData;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_following_languages",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<LanguageData> languageData;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_following_localities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "locality_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<LocalityData> localityData;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_following_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<TagData> tagData;
}
